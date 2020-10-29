package com.savin;

import com.savin.contracts.Contract;

public class Repository<E> {
    private static final int DEFAULT_CAPACITY = 100;
    private int size = 0; // size of the current repository
    private Object[] repository;

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
    }

    public int getSize() {
        return size;
    }

    public void addContract(Contract contract) {
        if (repository.length == size) {
            updateCapacity();
        }
        repository[size] = contract;
        size = size + 1;
    }

    public void deleteByID(int ID) {
        for (int i = 0; i < size; i++) {
            if (repository[i] instanceof Contract) {
                if (((Contract) repository[i]).getID() == ID) {
                    repository[i] = null;
                    size = size - 1;
                }
            }
        }
    }

    public Contract getByID(int ID) {
        for (int i = 0; i < size; i++) {
            if (repository[i] instanceof Contract) {
                if (((Contract) repository[i]).getID() == ID) {
                    return (Contract) repository[i];
                }
            }
        }
        return null;
    }
}
