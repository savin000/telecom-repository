package com.savin.entities;

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

    public int getAge() {
        if (birthDate != null) {
            return Period.between(birthDate, LocalDate.now()).getYears();
        } else {
            return 0;
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
