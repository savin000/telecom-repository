package com.savin.repository;

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

/**
 * A class to test class Repository
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class ContractRepositoryTest {
    /**
     * An array where persons are stored
     */
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

    @Test
    public void addContract_onlyOne() {
        ContractRepository repository = new ContractRepository();
        Contract wiredInternet = new WiredInternet(123, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.add(wiredInternet);

        expectedSize = 1;
        assertEquals(expectedSize, repository.getSize());
    }

    @Test
    public void addContract_threeDifferent() {
        ContractRepository repository = new ContractRepository();
        Contract wiredInternet = new WiredInternet(123, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract digitalTelevision = new DigitalTelevision(10, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 16, persons[1], ChannelPackage.ULTIMATE);
        Contract mobileCommunication = new MobileCommunication(99, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[1], 200, 200, 10);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.add(wiredInternet);
        expectedSize = 1;
        assertEquals(expectedSize, repository.getSize());

        repository.add(digitalTelevision);
        expectedSize = 2;
        assertEquals(expectedSize, repository.getSize());

        repository.add(mobileCommunication);
        expectedSize = 3;
        assertEquals(expectedSize, repository.getSize());
    }

    @Test
    public void should_updateCapacity_when_overrun() {
        ContractRepository repository = new ContractRepository(1);
        Contract wiredInternet = new WiredInternet(123, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract digitalTelevision = new DigitalTelevision(10, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 16, persons[1], ChannelPackage.ULTIMATE);

        repository.add(wiredInternet);
        int expectedSize = 1;
        assertEquals(expectedSize, repository.getSize());

        repository.add(digitalTelevision);
        expectedSize = 2;
        assertEquals(expectedSize, repository.getSize());
    }

    @Test
    public void deleteByID_correctID() {
        ContractRepository repository = new ContractRepository();
        Contract mobileCommunication = new MobileCommunication(99, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[1], 200, 200, 10);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.add(mobileCommunication);

        expectedSize = 1;
        assertEquals(expectedSize, repository.getSize());

        repository.deleteByID(99);

        expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());
    }

    @Test
    public void deleteByID_threeInARow_correctID() {
        ContractRepository repository = new ContractRepository();
        Contract mobileCommunication1 = new MobileCommunication(1, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[1], 200, 200, 10);
        Contract mobileCommunication2 = new MobileCommunication(2, LocalDate.of(2015, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[2], 200, 200, 10);
        Contract wiredInternet1 = new WiredInternet(3, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract wiredInternet2 = new WiredInternet(4, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[2], 50);
        Contract digitalTelevision1 = new DigitalTelevision(5, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 16, persons[1], ChannelPackage.ULTIMATE);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.add(mobileCommunication1);
        repository.add(mobileCommunication2);
        repository.add(wiredInternet1);
        repository.add(wiredInternet2);
        repository.add(digitalTelevision1);

        expectedSize = 5;
        assertEquals(expectedSize, repository.getSize());

        repository.deleteByID(1);
        repository.deleteByID(2);
        repository.deleteByID(3);

        expectedSize = 2;
        assertEquals(expectedSize, repository.getSize());
    }

    @Test
    public void deleteByID_incorrectID() {
        ContractRepository repository = new ContractRepository();
        Contract mobileCommunication = new MobileCommunication(99, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[1], 200, 200, 10);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.add(mobileCommunication);

        expectedSize = 1;
        assertEquals(expectedSize, repository.getSize());

        repository.deleteByID(101);

        assertEquals(expectedSize, repository.getSize());
    }

    @Test
    public void getByID_correctID() {
        ContractRepository repository = new ContractRepository();
        Contract wiredInternet = new WiredInternet(123, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.add(wiredInternet);

        assertEquals(wiredInternet, repository.getByID(123));
    }

    @Test
    public void getByID_incorrectID() {
        ContractRepository repository = new ContractRepository();
        Contract wiredInternet = new WiredInternet(123, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.add(wiredInternet);

        assertNotEquals(wiredInternet, repository.getByID(6));
    }

    @Test
    public void should_getByID_when_addedAfterOverrun() {
        ContractRepository repository = new ContractRepository(0);
        Contract wiredInternet = new WiredInternet(1, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract mobileCommunication = new MobileCommunication(99, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[1], 200, 200, 10);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.add(wiredInternet);
        repository.add(mobileCommunication);

        assertEquals(wiredInternet, repository.getByID(1));
        assertEquals(mobileCommunication, repository.getByID(99));
    }

    @Test
    public void should_getByID_when_threeInARow_deletedByID() {
        ContractRepository repository = new ContractRepository();
        Contract mobileCommunication1 = new MobileCommunication(1, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[1], 200, 200, 10);
        Contract mobileCommunication2 = new MobileCommunication(2, LocalDate.of(2015, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[2], 200, 200, 10);
        Contract wiredInternet1 = new WiredInternet(3, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract wiredInternet2 = new WiredInternet(4, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[2], 50);
        Contract digitalTelevision1 = new DigitalTelevision(5, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 16, persons[1], ChannelPackage.ULTIMATE);
        Contract digitalTelevision2 = new DigitalTelevision(6, LocalDate.of(2013, 6, 12),
                LocalDate.of(2022, 3, 12), 16, persons[0], ChannelPackage.SPORTS);
        Contract digitalTelevision3 = new DigitalTelevision(7, LocalDate.of(2016, 7, 12),
                LocalDate.of(2022, 3, 12), 16, persons[2], ChannelPackage.BASIC);

        int expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());

        repository.add(mobileCommunication1);
        repository.add(mobileCommunication2);
        repository.add(wiredInternet1);
        repository.add(wiredInternet2);
        repository.add(digitalTelevision1);
        repository.add(digitalTelevision2);
        repository.add(digitalTelevision3);

        repository.deleteByID(1);
        repository.deleteByID(3);
        repository.deleteByID(4);
        repository.deleteByID(5);

        assertEquals(mobileCommunication2, repository.getByID(2));
        assertEquals(digitalTelevision2, repository.getByID(6));
        assertEquals(digitalTelevision3, repository.getByID(7));

        expectedSize = 3;
        assertEquals(expectedSize, repository.getSize());
    }
}