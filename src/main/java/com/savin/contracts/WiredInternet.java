package com.savin.contracts;

import com.savin.entities.Person;

import java.time.LocalDate;

public class WiredInternet extends Contract {
    private double connectionSpeed;

    public WiredInternet(int ID, LocalDate startDate, LocalDate endDate, int contractNumber, Person contractHolder,
                  double connectionSpeed) {
        super(ID, startDate, endDate, contractNumber, contractHolder);
        this.connectionSpeed = connectionSpeed;
    }
}
