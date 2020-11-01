package com.savin;

import com.savin.contracts.Contract;

import java.util.logging.Logger;

public class Repository<E> {
    Logger log = Logger.getLogger(Repository.class.getName());

    private static final int DEFAULT_CAPACITY = 100;
    private int size = 0; // size of the current repository
    private Object[] repository;

    public int getSize() {
        return size;
    }

    Repository() {
        repository = new Object[DEFAULT_CAPACITY];
    }

    Repository(int capacity) {
        repository = new Object[capacity];
    }

    private void updateCapacity() {
        int newCapacity = repository.length + DEFAULT_CAPACITY;
        Object[] newRepository = new Object[newCapacity];
        System.arraycopy(repository, 0, newRepository, 0, repository.length);
        repository = newRepository;
        log.info("Capacity was increased");
    }

    public void addContract(Contract contract) {
        if (repository.length == size) {
            updateCapacity();
        }
        repository[size] = contract;
        log.info("Contract " + contract.getClass().getSimpleName() + " was added");
        size = size + 1;
    }

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
