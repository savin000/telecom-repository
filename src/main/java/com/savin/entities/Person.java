package com.savin.entities;

import com.savin.contracts.NoBirthDateException;

import java.time.LocalDate;
import java.time.Period;

public class Person {
    private int ID;
    private String fullName;
    private LocalDate birthDate;
    private String gender;
    private String passportDetails;

    public int getID() {
        return ID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getPassportDetails() {
        return passportDetails;
    }

    public int getAge() throws NoBirthDateException {
        try {
           return Period.between(birthDate, LocalDate.now()).getYears();
        } catch (NullPointerException e) {
            throw new NoBirthDateException(e);
        }
    }

    public Person(int ID, String fullName, LocalDate birthDate, String gender, String passportDetails) {
        this.ID = ID;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.passportDetails = passportDetails;
    }
}
