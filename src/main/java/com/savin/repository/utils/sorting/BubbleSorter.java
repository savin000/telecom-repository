package com.savin.repository.utils.sorting;

import java.util.Comparator;
import java.util.logging.Logger;

/**
 * This class represents Bubble sort implementation
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class BubbleSorter<E> implements Sorter<E> {
    Logger log = Logger.getLogger(BubbleSorter.class.getName());

    /**
     * Sorts the repository using Bubble sort algorithm
     * @param repository a repository to sort
     * @param repositorySize size of the repository
     * @param comparator compare criteria
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sort(Object[] repository, int repositorySize, Comparator<E> comparator) {
        for (int i = 0; i < repositorySize - 1; i++) {
            for (int j = 0; j < repositorySize - i - 1; j++) {
                if (comparator.compare((E) repository[j], (E) repository[j + 1]) > 0) {
                    E contract = (E) repository[j];
                    repository[j] = repository[j + 1];
                    repository[j + 1] = contract;
                }
            }
        }
        log.info("The repository is sorted using Bubble sort");
    }
}
