package com.savin.contracts;

import com.savin.entities.Person;

import java.time.LocalDate;

public abstract class Contract {
    private int ID;
    private LocalDate startDate;
    private LocalDate endDate;
    private int contractNumber;
    private Person contractHolder;

    public int getID() {
        return ID;
    }

    Contract(int ID, LocalDate startDate, LocalDate endDate, int contractNumber, Person contractHolder) {
        this.ID = ID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractNumber = contractNumber;
        this.contractHolder = contractHolder;
    }
}
