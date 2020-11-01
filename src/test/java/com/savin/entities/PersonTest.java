package com.savin.entities;

import com.savin.contracts.NoBirthDateException;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class PersonTest {
    @Test(expected = NoBirthDateException.class)
    public void should_throwNoBirthDateException_when_ageIsNull() throws NoBirthDateException {
        Person person = new Person(1, "John Doe", null, "male",
                "123456789");
        person.getAge();
    }

    @Test
    public void should_getAge_when_ageIsNotNull() throws NoBirthDateException {
        Person person = new Person(1, "John Doe", LocalDate.of(2001, 6, 18), "male",
                "123456789");
        int expectedAge = 19;
        assertEquals(expectedAge, person.getAge());
    }
}