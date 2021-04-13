package com.savin.utils.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * This is an adapter class which allows to save LocalDate instances as XML elements
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    /**
     * Unmarshalling method
     *
     * @param object object to marshal
     * @return unmarshalled date
     */
    public LocalDate unmarshal(String object) {
        return LocalDate.parse(object);
    }

    /**
     * Marshalling method
     *
     * @param localDate date to marshal
     * @return LocalDate as String
     */
    public String marshal(LocalDate localDate) {
        return localDate.toString();
    }
}