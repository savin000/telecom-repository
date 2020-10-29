package com.savin;

public class Repository<Contract> {
    private static final int DEFAULT_CAPACITY = 100;
    private int size = 0; // size of the current repository
    private Object[] repository;

    Repository() {
        repository = new Object[DEFAULT_CAPACITY];
    }

    private void updateCapacity() {
        int newCapacity = repository.length + DEFAULT_CAPACITY;
        Object[] newRepository = new Object[newCapacity];
        System.arraycopy(repository, 0, newRepository, 0, repository.length);
        repository = newRepository;
    }

    public void addContract(Contract contract) {
        if (repository.length == size) {
            updateCapacity();
        }
        repository[size] = contract;
        size = size + 1;
    }

    public void deleteByID(int ID) {
        repository[ID] = null;
        size = size - 1;
    }

    @SuppressWarnings("unchecked")
    public Contract getByID(int ID) {
        return (Contract) repository[ID];
    }
}
