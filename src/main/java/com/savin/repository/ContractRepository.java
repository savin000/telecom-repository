package com.savin.repository;

import com.savin.contracts.Contract;

import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * This class represents repository, where all contracts are collected.
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class ContractRepository implements Repository<Contract> {
    Logger log = Logger.getLogger(ContractRepository.class.getName());

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
    public ContractRepository() {
        repository = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Creates a new Repository with the specified capacity
     * @param capacity specified capacity
     */
    public ContractRepository(int capacity) {
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
    public void add(Contract contract) {
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
        Object[] newRepository = new Object[repository.length];
        int i = 0;

        for (Object obj : repository) {
            if (obj instanceof Contract) {
                if (((Contract) obj).getID() != ID) {
                    newRepository[i] = obj;
                    i = i + 1;
                } else {
                    log.info("Contract with ID " + ID + " was deleted");
                    size = size - 1;
                }
            }
        }
        repository = newRepository;
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

    /**
     * Searches the repository by various criteria
     * @param predicate search criteria
     * @return a new repository that contains contracts that meet the search criteria
     */
    public Repository<Contract> searchBy(Predicate<Contract> predicate) {
        Repository<Contract> searchResult = new ContractRepository();
        for (int i = 0; i < size; i++) {
            if (predicate.test((Contract) repository[i])) {
                log.info("Matching contract found");
                searchResult.add((Contract) repository[i]);
            }
        }
        return searchResult;
    }
}
