package com.savin;

import com.savin.contracts.Contract;

import java.util.logging.Logger;

/**
 * This class represents repository, where all contracts are collected.
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class Repository<E> {
    Logger log = Logger.getLogger(Repository.class.getName());

    /**
     * Default capacity of the repository (100 contracts)
     */
    private static final int DEFAULT_CAPACITY = 100;

    /**
     * Current size of the repository
     */
    private int size = 0;

    /**
     * An array where contracts are stored
     */
    private Object[] repository;

    /**
     * @return size of the repository
     */
    public int getSize() {
        return size;
    }

    /**
     * Creates a new Repository with default capacity
     */
    public Repository() {
        repository = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Creates a new Repository with the specified capacity
     * @param capacity specified capacity
     */
    public Repository(int capacity) {
        repository = new Object[capacity];
    }

    /**
     * This method increases capacity of the repository if required
     */
    private void updateCapacity() {
        int newCapacity = repository.length + DEFAULT_CAPACITY;
        Object[] newRepository = new Object[newCapacity];
        System.arraycopy(repository, 0, newRepository, 0, repository.length);
        repository = newRepository;
        log.info("Capacity was increased");
    }

    /**
     * This method adds the contract to the repository
     * @param contract the contract to add
     */
    public void addContract(Contract contract) {
        if (repository.length == size) {
            updateCapacity();
        }
        repository[size] = contract;
        log.info("Contract " + contract.getClass().getSimpleName() + " was added");
        size = size + 1;
    }

    /**
     * This method removes a contract by its ID
     * @param ID this contract's ID
     */
    public void deleteByID(int ID) {
        for (int i = 0; i < size; i++) {
            if (repository[i] instanceof Contract) {
                if (((Contract) repository[i]).getID() == ID) {
                    repository[i] = null;
                    log.info("Contract with ID " + ID + " was deleted");
                    size = size - 1;
                }
            }
        }
    }

    /**
     * This method gets a contract by its ID
     * @param ID this contract's ID
     * @return contract
     */
    public Contract getByID(int ID) {
        for (int i = 0; i < size; i++) {
            if (repository[i] instanceof Contract) {
                if (((Contract) repository[i]).getID() == ID) {
                    log.info("Contract with ID " + ID + " was successfully got by ID");
                    return (Contract) repository[i];
                }
            }
        }
        return null;
    }
}
