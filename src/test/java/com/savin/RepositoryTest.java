package com.savin;

import com.savin.contracts.Contract;
import com.savin.contracts.DigitalTelevision;
import com.savin.contracts.MobileCommunication;
import com.savin.contracts.WiredInternet;
import com.savin.entities.Person;
import com.savin.enums.ChannelPackage;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RepositoryTest {
    Person[] persons = new Person[3];

    @Before
    public void setUpPersons() {
        Person person1 = new Person(1, "John Doe", LocalDate.of(2001, 1, 10), "male",
                "123456789");
        persons[0] = person1;
        Person person2 = new Person(2, "Jane Doe", LocalDate.of(2005, 12, 23), "female",
                "987654321");
        persons[1] = person2;
        Person person3 = new Person(3, "J", LocalDate.of(2000, 6, 18), "female",
                "A1CDEFGHI");
        persons[2] = person3;
    }

    // We do not really care about correctness of added contracts,
    // we are interested in the fact of addition contracts to repository
    // Data reliability will be checked by "getById" method

    @Test
    public void addContract_OnlyOne_True() {
        Repository<Contract> repository = new Repository<>();
        Contract wiredInternet = new WiredInternet(123, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.addContract(wiredInternet);

        expectedSize = 1;
        assertEquals(expectedSize, repository.getSize());
        expectedSize = 2;
        assertNotEquals(expectedSize, repository.getSize());
    }

    @Test
    public void addContract_ThreeDifferent_True() {
        Repository<Contract> repository = new Repository<>();
        Contract wiredInternet = new WiredInternet(123, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract digitalTelevision = new DigitalTelevision(10, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 16, persons[1], ChannelPackage.ULTIMATE);
        Contract mobileCommunication = new MobileCommunication(99, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[1], 200, 200, 10);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.addContract(wiredInternet);
        expectedSize = 1;
        assertEquals(expectedSize, repository.getSize());
        repository.addContract(digitalTelevision);
        expectedSize = 2;
        assertEquals(expectedSize, repository.getSize());
        repository.addContract(mobileCommunication);
        expectedSize = 3;
        assertEquals(expectedSize, repository.getSize());
        expectedSize = 0;
        assertNotEquals(expectedSize, repository.getSize());
    }

    @Test
    public void should_UpdateCapacity_When_Overrun() {
        Repository<Contract> repository = new Repository<>(1);
        Contract wiredInternet = new WiredInternet(123, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract digitalTelevision = new DigitalTelevision(10, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 16, persons[1], ChannelPackage.ULTIMATE);

        repository.addContract(wiredInternet);
        int expectedSize = 1;
        assertEquals(expectedSize, repository.getSize());
        repository.addContract(digitalTelevision);
        expectedSize = 2;
        assertEquals(expectedSize, repository.getSize());
    }

    @Test
    public void deleteByID_CorrectID_True() {
        Repository<Contract> repository = new Repository<>();
        Contract mobileCommunication = new MobileCommunication(99, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[1], 200, 200, 10);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.addContract(mobileCommunication);

        expectedSize = 1;
        assertEquals(expectedSize, repository.getSize());

        repository.deleteByID(99);

        expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());
    }

    @Test
    public void deleteByID_IncorrectID_True() {
        Repository<Contract> repository = new Repository<>();
        Contract mobileCommunication = new MobileCommunication(99, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[1], 200, 200, 10);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.addContract(mobileCommunication);

        expectedSize = 1;
        assertEquals(expectedSize, repository.getSize());

        repository.deleteByID(101);

        assertEquals(expectedSize, repository.getSize());
    }

    @Test
    public void getByID_CorrectID_True() {
        Repository<Contract> repository = new Repository<>();
        Contract wiredInternet = new WiredInternet(123, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.addContract(wiredInternet);

        assertEquals(repository.getByID(123), wiredInternet);
    }

    @Test
    public void getByID_IncorrectID_True() {
        Repository<Contract> repository = new Repository<>();
        Contract wiredInternet = new WiredInternet(123, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.addContract(wiredInternet);

        assertNotEquals(repository.getByID(6), wiredInternet);
    }

}