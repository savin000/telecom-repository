package com.savin.utils.database.connection;

import com.savin.contracts.Contract;
import com.savin.contracts.DigitalTelevision;
import com.savin.contracts.MobileCommunication;
import com.savin.contracts.WiredInternet;
import com.savin.entities.Person;
import com.savin.enums.ChannelPackage;
import com.savin.repository.core.ContractRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

/**
 * A class to test DatabaseOperations class
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class DatabaseOperationsTest {

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
    public void saveToDatabase() {
        ContractRepository repository = new ContractRepository();
        Contract wiredInternet = new WiredInternet(123, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract digitalTelevision = new DigitalTelevision(10, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 16, persons[1], ChannelPackage.ULTIMATE);
        Contract mobileCommunication = new MobileCommunication(99, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[1], 200, 200, 10);

        repository.add(wiredInternet);
        repository.add(digitalTelevision);
        repository.add(mobileCommunication);

        DatabaseOperations databaseOperations = new DatabaseOperations();
        databaseOperations.saveContractRepository(repository);
    }
}