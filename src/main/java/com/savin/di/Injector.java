package com.savin.di;

import com.google.common.reflect.ClassPath;
import com.savin.annotations.AutoInjectable;
import com.savin.annotations.Configuration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Dependency injection class. It is used to inject necessary objects into fields
 *
 * @author Mikhail Savin
 * @since 1.0
 */
@Configuration(packages = {"com.savin"})
public class Injector {

    /**
     * This method implements dependency injection
     *
     * @param obj the object to inject dependencies
     * @param <E> generic type of the class
     * @throws IllegalAccessException illegal access
     * @throws IOException failed I/O operation
     * @throws InstantiationException something went wrong during instantiation
     * @throws NoInjectableClassesException if there are no injectable classes
     * @throws SurplusOfInjectableClassesException if there are too many injectable classes
     */
    public static <E> void inject(E obj) throws
            NoInjectableClassesException, SurplusOfInjectableClassesException {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());

        for (String pkg : Injector.class.getAnnotation(Configuration.class).packages()) {
            Set<ClassPath.ClassInfo> allClasses = classPath.getTopLevelClassesRecursive(pkg);
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(AutoInjectable.class)) {
                    field.setAccessible(true);
                    Class<?> clazz = field.getAnnotation(AutoInjectable.class).clazz();
                    List<Object> instances = new ArrayList<>();
                    for (ClassPath.ClassInfo ci : allClasses) {
                        if (clazz.isAssignableFrom(ci.load()) && !ci.load().isInterface()) {
                            instances.add(ci.load().newInstance());
                        }
                    }
                    if (Collection.class.isAssignableFrom(field.getType())) {
                        field.set(obj, instances);
                    } else {
                        if (instances.size() == 1) {
                            field.set(obj, instances.get(0));
                        } else if (instances.size() > 1) {
                            throw new SurplusOfInjectableClassesException("Surplus of injectable classes");
                        } else {
                            throw new NoInjectableClassesException("No injectable classes");
                        }
                    }
                }
            }
        }
    }
}
