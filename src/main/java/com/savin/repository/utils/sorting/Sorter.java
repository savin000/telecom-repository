package com.savin.repository.utils.sorting;

import java.util.Comparator;

/**
 * Repository sorter
 *
 * @author Mikhail Savin
 * @see BubbleSorter
 * @see ShellSorter
 * @since 1.0
 */
public interface Sorter<E> {

    /**
     * Sorts the repository by various criteria using Comparator
     * @param repository a repository to sort
     * @param repositorySize size of the repository
     * @param comparator compare criteria
     */
    void sort(Object[] repository, int repositorySize, Comparator<E> comparator);
}
