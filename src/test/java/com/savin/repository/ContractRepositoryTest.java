package com.savin.repository;

import com.savin.contracts.*;
import com.savin.entities.Person;
import com.savin.enums.ChannelPackage;
import com.savin.repository.core.ContractRepository;
import com.savin.repository.utils.sorting.BubbleSorter;
import com.savin.repository.utils.sorting.ShellSorter;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.function.Predicate;

import static org.junit.Assert.*;

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
    Person[] persons = new Person[4];

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
        Person noBirthDatePerson = new Person(4, "NB DP", null, "female",
                "A1C2E45G36");
        persons[3] = noBirthDatePerson;
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
        assertEquals(wiredInternet, repository.getByIndex(0));
        assertNull(repository.getByIndex(1));
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

        assertEquals(wiredInternet, repository.getByIndex(0));
        assertEquals(digitalTelevision, repository.getByIndex(1));
        assertEquals(mobileCommunication, repository.getByIndex(2));
        assertNull(repository.getByIndex(3));
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
        assertEquals(mobileCommunication, repository.getByIndex(0));

        repository.deleteByID(99);

        expectedSize = 0;
        assertEquals(expectedSize, repository.getSize());
        assertNull(repository.getByIndex(0));
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

        assertEquals(wiredInternet2, repository.getByIndex(3));
        assertEquals(digitalTelevision1, repository.getByIndex(4));

        repository.deleteByID(1);
        repository.deleteByID(2);
        repository.deleteByID(3);

        expectedSize = 2;
        assertEquals(expectedSize, repository.getSize());

        // wiredInternet2 now has index=0
        assertEquals(wiredInternet2, repository.getByIndex(0));
        // digitalTelevision1 now has index=1
        assertEquals(digitalTelevision1, repository.getByIndex(1));
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
        assertEquals(mobileCommunication, repository.getByIndex(0));
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

    @Test
    public void searchBy_IDGreaterThan10() {
        ContractRepository repository = new ContractRepository();
        Contract mobileCommunication1 = new MobileCommunication(1, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[1], 200, 200, 10);
        Contract mobileCommunication2 = new MobileCommunication(2, LocalDate.of(2015, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[2], 200, 200, 10);
        Contract wiredInternet1 = new WiredInternet(30, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract wiredInternet2 = new WiredInternet(4, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[2], 50);
        Contract digitalTelevision1 = new DigitalTelevision(50, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 16, persons[1], ChannelPackage.ULTIMATE);
        Contract digitalTelevision2 = new DigitalTelevision(6, LocalDate.of(2013, 6, 12),
                LocalDate.of(2022, 3, 12), 16, persons[0], ChannelPackage.SPORTS);
        Contract digitalTelevision3 = new DigitalTelevision(70, LocalDate.of(2016, 7, 12),
                LocalDate.of(2022, 3, 12), 16, persons[2], ChannelPackage.BASIC);

        repository.add(mobileCommunication1);
        repository.add(mobileCommunication2);
        repository.add(wiredInternet1);
        repository.add(wiredInternet2);
        repository.add(digitalTelevision1);
        repository.add(digitalTelevision2);
        repository.add(digitalTelevision3);

        Predicate<Contract> greaterThan10 = (p) -> p.getID() > 10;

        assertNull(repository.searchBy(greaterThan10).getByID(1));
        assertNull(repository.searchBy(greaterThan10).getByID(2));
        Contract expectedResult = wiredInternet1;
        assertEquals(expectedResult, repository.searchBy(greaterThan10).getByID(30));
        expectedResult = digitalTelevision1;
        assertEquals(expectedResult, repository.searchBy(greaterThan10).getByID(50));
        expectedResult = digitalTelevision3;
        assertEquals(expectedResult, repository.searchBy(greaterThan10).getByID(70));
        assertNull(repository.searchBy(greaterThan10).getByID(4));
        assertNull(repository.searchBy(greaterThan10).getByID(6));
        assertNull(repository.searchBy(greaterThan10).getByID(repository.getSize()));
    }

    @Test
    public void searchBy_fullNameOfTheContractHolder() {
        ContractRepository repository = new ContractRepository();
        Contract wiredInternet = new WiredInternet(123, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract digitalTelevision = new DigitalTelevision(10, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 16, persons[1], ChannelPackage.ULTIMATE);
        Contract mobileCommunication = new MobileCommunication(99, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[2], 200, 200, 10);

        repository.add(wiredInternet);
        repository.add(digitalTelevision);
        repository.add(mobileCommunication);

        int expectedSize = 3;
        assertEquals(expectedSize, repository.getSize());

        Predicate<Contract> fullName = (p) -> p.getContractHolder().getFullName().equals("John Doe");
        assertEquals(wiredInternet, repository.searchBy(fullName).getByID(123));
        assertNull(repository.searchBy(fullName).getByID(10));
        assertNull(repository.searchBy(fullName).getByID(99));
    }

    @Test
    public void searchBy_contractNumber() {
        ContractRepository repository = new ContractRepository();
        Contract wiredInternet = new WiredInternet(123, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract digitalTelevision = new DigitalTelevision(10, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 16, persons[1], ChannelPackage.ULTIMATE);
        Contract mobileCommunication = new MobileCommunication(99, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[2], 200, 200, 10);

        repository.add(wiredInternet);
        repository.add(digitalTelevision);
        repository.add(mobileCommunication);

        int expectedSize = 3;
        assertEquals(expectedSize, repository.getSize());

        Predicate<Contract> contractPredicate = (p) -> p.getContractNumber() == 17;

        assertNull(repository.searchBy(contractPredicate).getByID(123));
        assertNull(repository.searchBy(contractPredicate).getByID(10));
        assertEquals(mobileCommunication, repository.searchBy(contractPredicate).getByID(99));
    }

    @Test
    public void searchBy_ageOfTheContractHolderGreaterThan18() {
        ContractRepository repository = new ContractRepository();
        Contract wiredInternet1 = new WiredInternet(1, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract wiredInternet2 = new WiredInternet(2, LocalDate.of(2020, 1, 15), LocalDate.now(),
                12, persons[3], 30);
        Contract digitalTelevision1 = new DigitalTelevision(3, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 56, persons[1], ChannelPackage.ULTIMATE);
        Contract digitalTelevision2 = new DigitalTelevision(4, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 124, persons[2], ChannelPackage.BASIC);

        repository.add(wiredInternet1);
        repository.add(wiredInternet2);
        repository.add(digitalTelevision1);
        repository.add(digitalTelevision2);

        int expectedSize = 4;
        assertEquals(expectedSize, repository.getSize());

        Predicate<Contract> ageIsGreaterThan18 = (p) -> {
            try {
                return p.getContractHolder().getAge() > 18;
            } catch (NoBirthDateException e) {
                e.printStackTrace();
            }
            return false;
        };

        assertEquals(wiredInternet1, repository.searchBy(ageIsGreaterThan18).getByID(1));
        assertNull(repository.searchBy(ageIsGreaterThan18).getByID(2));
        assertNull(repository.searchBy(ageIsGreaterThan18).getByID(3));
        assertEquals(digitalTelevision2, repository.searchBy(ageIsGreaterThan18).getByID(4));
    }

    @Test
    public void sortBy_ID_withBubbleSort() {
        ContractRepository repository = new ContractRepository();
        Contract wiredInternet1 = new WiredInternet(1, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract wiredInternet2 = new WiredInternet(4, LocalDate.of(2020, 1, 15), LocalDate.now(),
                12, persons[3], 30);
        Contract digitalTelevision1 = new DigitalTelevision(3, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 56, persons[1], ChannelPackage.ULTIMATE);
        Contract digitalTelevision2 = new DigitalTelevision(2, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 124, persons[2], ChannelPackage.BASIC);

        repository.add(wiredInternet1);
        repository.add(wiredInternet2);
        repository.add(digitalTelevision1);
        repository.add(digitalTelevision2);

        int expectedSize = 4;
        assertEquals(expectedSize, repository.getSize());

        Comparator<Contract> ascendingID = Comparator.comparing(Contract::getID);

        assertEquals(wiredInternet1, repository.getByIndex(0));
        assertEquals(wiredInternet2, repository.getByIndex(1));
        assertEquals(digitalTelevision1, repository.getByIndex(2));
        assertEquals(digitalTelevision2, repository.getByIndex(3));

        repository.setSortingAlgorithm(new BubbleSorter<>());
        repository.sortBy(ascendingID);

        assertEquals(wiredInternet1, repository.getByIndex(0));
        assertEquals(wiredInternet2, repository.getByIndex(3));
        assertEquals(digitalTelevision1, repository.getByIndex(2));
        assertEquals(digitalTelevision2, repository.getByIndex(1));
    }

    @Test
    public void sortBy_ID_withShellSort() {
        ContractRepository repository = new ContractRepository();
        Contract wiredInternet1 = new WiredInternet(1, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract wiredInternet2 = new WiredInternet(4, LocalDate.of(2020, 1, 15), LocalDate.now(),
                12, persons[3], 30);
        Contract digitalTelevision1 = new DigitalTelevision(3, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 56, persons[1], ChannelPackage.ULTIMATE);
        Contract digitalTelevision2 = new DigitalTelevision(2, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 124, persons[2], ChannelPackage.BASIC);

        repository.add(wiredInternet1);
        repository.add(wiredInternet2);
        repository.add(digitalTelevision1);
        repository.add(digitalTelevision2);

        int expectedSize = 4;
        assertEquals(expectedSize, repository.getSize());

        Comparator<Contract> ascendingID = Comparator.comparing(Contract::getID);

        assertEquals(wiredInternet1, repository.getByIndex(0));
        assertEquals(wiredInternet2, repository.getByIndex(1));
        assertEquals(digitalTelevision1, repository.getByIndex(2));
        assertEquals(digitalTelevision2, repository.getByIndex(3));

        repository.setSortingAlgorithm(new ShellSorter<>());
        repository.sortBy(ascendingID);

        assertEquals(wiredInternet1, repository.getByIndex(0));
        assertEquals(wiredInternet2, repository.getByIndex(3));
        assertEquals(digitalTelevision1, repository.getByIndex(2));
        assertEquals(digitalTelevision2, repository.getByIndex(1));
    }

    @Test
    public void sortBy_ageOfContractHolder_withBubbleSort() {
        ContractRepository repository = new ContractRepository();
        Contract mobileCommunication1 = new MobileCommunication(1, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[1], 200, 200, 10);
        Contract wiredInternet1 = new WiredInternet(30, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract wiredInternet2 = new WiredInternet(4, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[2], 50);
        // persons[3].getAge() == null;
        Contract digitalTelevision3 = new DigitalTelevision(70, LocalDate.of(2016, 7, 12),
                LocalDate.of(2022, 3, 12), 16, persons[3], ChannelPackage.BASIC);

        repository.add(mobileCommunication1);
        repository.add(wiredInternet1);
        repository.add(wiredInternet2);
        repository.add(digitalTelevision3);

        int expectedSize = 4;
        assertEquals(expectedSize, repository.getSize());

        Comparator<Contract> ascendingAge = (c1, c2) ->
        {
            try {
                return Integer.signum(c1.getContractHolder().getAge() - c2.getContractHolder().getAge());
            } catch (NoBirthDateException e) {
                return 1;
            }
        };

        assertEquals(mobileCommunication1, repository.getByIndex(0));
        assertEquals(wiredInternet1, repository.getByIndex(1));
        assertEquals(wiredInternet2, repository.getByIndex(2));
        assertEquals(digitalTelevision3, repository.getByIndex(3));

        repository.setSortingAlgorithm(new BubbleSorter<>());
        repository.sortBy(ascendingAge);

        assertEquals(mobileCommunication1, repository.getByIndex(1));
        assertEquals(wiredInternet1, repository.getByIndex(2));
        assertEquals(wiredInternet2, repository.getByIndex(3));
        assertEquals(digitalTelevision3, repository.getByIndex(0));
    }

    @Test
    public void sortBy_ageOfContractHolder_withShellSort() {
        ContractRepository repository = new ContractRepository();
        Contract mobileCommunication1 = new MobileCommunication(1, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[1], 200, 200, 10);
        Contract wiredInternet1 = new WiredInternet(30, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract wiredInternet2 = new WiredInternet(4, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[2], 50);
        // persons[3].getAge() == null;
        Contract digitalTelevision3 = new DigitalTelevision(70, LocalDate.of(2016, 7, 12),
                LocalDate.of(2022, 3, 12), 16, persons[3], ChannelPackage.BASIC);

        repository.add(mobileCommunication1);
        repository.add(wiredInternet1);
        repository.add(wiredInternet2);
        repository.add(digitalTelevision3);

        int expectedSize = 4;
        assertEquals(expectedSize, repository.getSize());

        Comparator<Contract> ascendingAge = (c1, c2) ->
        {
            try {
                return Integer.signum(c1.getContractHolder().getAge() - c2.getContractHolder().getAge());
            } catch (NoBirthDateException e) {
                return 1;
            }
        };

        assertEquals(mobileCommunication1, repository.getByIndex(0));
        assertEquals(wiredInternet1, repository.getByIndex(1));
        assertEquals(wiredInternet2, repository.getByIndex(2));
        assertEquals(digitalTelevision3, repository.getByIndex(3));

        repository.setSortingAlgorithm(new ShellSorter<>());
        repository.sortBy(ascendingAge);

        assertEquals(mobileCommunication1, repository.getByIndex(1));
        assertEquals(wiredInternet1, repository.getByIndex(2));
        assertEquals(wiredInternet2, repository.getByIndex(3));
        assertEquals(digitalTelevision3, repository.getByIndex(0));
    }
}