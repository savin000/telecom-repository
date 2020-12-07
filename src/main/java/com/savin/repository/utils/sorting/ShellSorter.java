package com.savin.repository.utils.sorting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;

/**
 * This class represents Shell sort implementation
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class ShellSorter<E> implements Sorter<E> {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Sorts the repository using Shell sort algorithm
     * @param repository a repository to sort
     * @param repositorySize size of the repository
     * @param comparator compare criteria
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sort(Object[] repository, int repositorySize, Comparator<E> comparator) {
        for (int h = repositorySize / 2; h > 0; h = h / 2) {
            for (int i = h; i < repositorySize; i++) {
                int j;
                E contract = (E) repository[i];
                for (j = i; j >= h && comparator.compare((E) repository[j - h], contract) > 0; j = j - h) {
                    repository[j] = repository[j - h];
                }
                repository[j] = contract;
            }
        }
        logger.info("The repository is sorted using Shell sort");
    }
}
