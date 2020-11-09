package com.savin.repository.core;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Repository for storing data
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public interface Repository<E> {

    /**
     * This method adds the element to the repository
     * @param elem the element to add
     */
    void add(E elem);

    /**
     * This method removes an element by its ID
     * @param ID this element's ID
     */
    void deleteByID(int ID);

    /**
     * This method gets an element by its ID
     * @param ID this element's ID
     * @return E
     */
    E getByID(int ID);

    /**
     * Searches the repository by various criteria
     * @param predicate search criteria
     * @return a new repository that contains elements that meet the search criteria
     */
    Repository<E> searchBy(Predicate<E> predicate);

    /**
     * Sorts the repository by various criteria
     * @param comparator compare criteria
     */
    void sortBy(Comparator<E> comparator);
}
