package com.savin.entities;

import com.savin.utils.xml.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.Period;

/**
 * This class represents person model in a system
 *
 * @author Mikhail Savin
 * @since 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

    /**
     * A number which uniquely identifies a person
     */
    @XmlElement(name = "id")
    private int id;

    /**
     * Full name of a person
     */
    @XmlElement(name = "fullName")
    private String fullName;

    /**
     * Person's date of birth
     */
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @XmlElement(name = "birthDate")
    private LocalDate birthDate;

    /**
     * Person's gender (may not be specified)
     */
    @XmlElement(name = "gender")
    private String gender;

    /**
     * Person's passport details
     */
    @XmlElement(name = "passportDetails")
    private String passportDetails;

    /**
     * @return this person's id
     */
    public int getID() {
        return id;
    }

    /**
     * @param id id to set
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * @return this person's full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName person's full name to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return this person's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender person's gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return this person's passport details
     */
    public String getPassportDetails() {
        return passportDetails;
    }

    /**
     * @param passportDetails person's passport details to set
     */
    public void setPassportDetails(String passportDetails) {
        this.passportDetails = passportDetails;
    }

    /**
     * This method calculates a person's age based on the specified date of birth and the current date
     * @return person's age
     * @throws NoBirthDateException if birth date is null
     */
    public int getAge() throws NoBirthDateException {
        try {
           return Period.between(birthDate, LocalDate.now()).getYears();
        } catch (NullPointerException e) {
            throw new NoBirthDateException(e);
        }
    }

    /**
     * @return this person's date of birth
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate person's date of birth to set (a date without a time-zone in the ISO-8601 calendar system)
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Default constructor (added as it is used by JAXB)
     */
    public Person() {
    }

    /**
     * Creates a new Person with the given id, full name, date of birth, gender and passport details
     *
     * @param id set the initial value for the class attribute id
     * @param fullName person's full name to set
     * @param birthDate person's date of birth to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param gender person's gender to set
     * @param passportDetails person's passport details to set
     */
    public Person(int id, String fullName, LocalDate birthDate, String gender, String passportDetails) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.passportDetails = passportDetails;
    }
}
