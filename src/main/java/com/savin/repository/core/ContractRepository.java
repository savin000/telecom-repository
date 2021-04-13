package com.savin.repository.core;

import com.savin.annotations.AutoInjectable;
import com.savin.contracts.Contract;
import com.savin.contracts.DigitalTelevision;
import com.savin.contracts.MobileCommunication;
import com.savin.contracts.WiredInternet;
import com.savin.repository.utils.sorting.BubbleSorter;
import com.savin.repository.utils.sorting.Sorter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.annotation.*;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * This class represents repository, where all contracts are collected.
 *
 * @author Mikhail Savin
 * @since 1.0
 */
@XmlRootElement(name = "contractRepository")
@XmlAccessorType(XmlAccessType.FIELD)
public class ContractRepository implements Repository<Contract> {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * A sorter for sorting the repository
     */
    @XmlTransient
    @AutoInjectable(clazz = BubbleSorter.class)
    private Sorter<Contract> sorter;

    /**
     * Default capacity of the repository (100 contracts)
     */
    @XmlTransient
    private static final int DEFAULT_CAPACITY = 100;

    /**
     * Current size of the repository
     */
    @XmlTransient
    private int size = 0;

    /**
     * An array where contracts are stored
     */
    @XmlElementWrapper
    @XmlElements({
            @XmlElement(name = "wiredInternet", type = WiredInternet.class),
            @XmlElement(name = "digitalTelevision", type = DigitalTelevision.class),
            @XmlElement(name = "mobileCommunication", type = MobileCommunication.class)
    })
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
     * @param sorter sorting algorithm to set
     */
    public void setSortingAlgorithm(Sorter<Contract> sorter) {
        this.sorter = sorter;
    }

    /**
     * This method increases capacity of the repository if required
     */
    private void updateCapacity() {
        int newCapacity = repository.length + DEFAULT_CAPACITY;
        Object[] newRepository = new Object[newCapacity];
        System.arraycopy(repository, 0, newRepository, 0, repository.length);
        repository = newRepository;
        LOGGER.debug("Capacity was increased");
    }

    /**
     * This method adds the contract to the repository
     * @param contract the contract to add
     */
    @Override
    public void add(Contract contract) {
        if (repository.length == size) {
            updateCapacity();
        }
        repository[size] = contract;
        LOGGER.debug("Contract {} was added", contract.getClass().getSimpleName());
        size = size + 1;
    }

    /**
     * This method removes a contract by its ID
     * @param ID this contract's ID
     */
    @Override
    public void deleteByID(int ID) {
        Object[] newRepository = new Object[repository.length];
        int i = 0;

        for (Object obj : repository) {
            if (obj instanceof Contract) {
                if (((Contract) obj).getID() != ID) {
                    newRepository[i] = obj;
                    i = i + 1;
                } else {
                    LOGGER.debug("Contract with ID {} was deleted", ID);
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
    @Override
    public Contract getByID(int ID) {
        for (int i = 0; i < size; i++) {
            if (repository[i] instanceof Contract) {
                if (((Contract) repository[i]).getID() == ID) {
                    LOGGER.debug("Contract with ID {} was successfully got by ID", ID);
                    return (Contract) repository[i];
                }
            }
        }
        return null;
    }

    /**
     * This method gets a contract by its index
     * @param index this contract's index (starting from 0)
     * @return contract
     */
    public Contract getByIndex(int index) {
        for (int i = 0; i < size; i++) {
            if (i == index) {
                LOGGER.debug("Contract with index {} was successfully got by index", index);
                return (Contract) repository[i];
            }
        }
        return null;
    }

    /**
     * Searches the repository by various criteria
     * @param predicate search criteria
     * @return a new repository that contains contracts that meet the search criteria
     */
    @Override
    public Repository<Contract> searchBy(Predicate<Contract> predicate) {
        Repository<Contract> searchResult = new ContractRepository();
        for (int i = 0; i < size; i++) {
            if (predicate.test((Contract) repository[i])) {
                LOGGER.debug("Matching contract found");
                searchResult.add((Contract) repository[i]);
            }
        }
        return searchResult;
    }

    /**
     * Sorts the repository by various criteria.
     * Several sorting algorithms implemented
     * @param comparator compare criteria
     */
    @Override
    public void sortBy(Comparator<Contract> comparator) {
            sorter.sort(repository, size, comparator);
    }
}
