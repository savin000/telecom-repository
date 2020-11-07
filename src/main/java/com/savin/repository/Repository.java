package com.savin.repository;

/**
 * Generic repository for storing data
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
}
