package com.savin.utils.database.connection;

import com.savin.contracts.Contract;
import com.savin.contracts.DigitalTelevision;
import com.savin.contracts.MobileCommunication;
import com.savin.contracts.WiredInternet;
import com.savin.entities.Person;
import com.savin.enums.ChannelPackage;
import com.savin.repository.core.ContractRepository;
import com.savin.utils.database.DatabaseOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

/**
 * A class to test DatabaseOperations class
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class DatabaseOperationsTest {
    private static final Logger LOGGER = LogManager.getLogger();

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
    }

    @Test
    public void saveRepositoryToDatabase() {
        ContractRepository repository = new ContractRepository();
        Contract mobileCommunication = new MobileCommunication(99, LocalDate.of(2017, 7, 30),
                LocalDate.of(2024, 9, 1), 17, persons[1], 200, 200, 10);
        Contract wiredInternet1 = new WiredInternet(123, LocalDate.of(2020, 1, 15), LocalDate.now(),
                15, persons[0], 100);
        Contract digitalTelevision1 = new DigitalTelevision(10, LocalDate.of(2019, 3, 12),
                LocalDate.of(2022, 3, 12), 16, persons[2], ChannelPackage.ULTIMATE);
        Contract wiredInternet2 = new WiredInternet(66, LocalDate.of(2016, 2, 13), LocalDate.now(),
                4, persons[1], 200);
        Contract digitalTelevision2 = new DigitalTelevision(3, LocalDate.of(2019, 3, 12),
                LocalDate.of(2024, 3, 12), 16, persons[0], ChannelPackage.BASIC);


        repository.add(wiredInternet1);
        repository.add(wiredInternet2);
        repository.add(digitalTelevision1);
        repository.add(digitalTelevision2);
        repository.add(mobileCommunication);

        DatabaseOperations databaseOperations = new DatabaseOperations();
        databaseOperations.createPersonTableIfNotExists();
        databaseOperations.createContractTableIfNotExists();
        databaseOperations.saveContractRepository(repository);
    }

    @Test
    public void getRepositoryFromDatabase() {
        ContractRepository repository;

        DatabaseOperations databaseOperations = new DatabaseOperations();
        repository = databaseOperations.getContractRepository();

        for (int i = 0; i < repository.getSize(); i++) {
            LOGGER.info("ID {}", repository.getByIndex(i).getID());
            LOGGER.info("Start date {}", repository.getByIndex(i).getStartDate());
            LOGGER.info("End date {}", repository.getByIndex(i).getEndDate());
            LOGGER.info("Contract number {}", repository.getByIndex(i).getContractNumber());
            LOGGER.info("Contract holder's ID {}", repository.getByIndex(i).getContractHolder().getID());
            LOGGER.info("Contract holder's full name {}", repository.getByIndex(i).getContractHolder().getFullName());
            LOGGER.info("Contract holder's birth date {}", repository.getByIndex(i).getContractHolder().getBirthDate());
            LOGGER.info("Contract holder's gender {}", repository.getByIndex(i).getContractHolder().getGender());
            LOGGER.info("Contract holder's passport details {}", repository.getByIndex(i).getContractHolder().getPassportDetails());

            if (repository.getByIndex(i) instanceof MobileCommunication) {
                LOGGER.info("Minutes: {}", ((MobileCommunication) repository.getByIndex(i)).getMinutes());
                LOGGER.info("SMS: {}", ((MobileCommunication) repository.getByIndex(i)).getSms());
                LOGGER.info("Traffic (GB): {}", ((MobileCommunication) repository.getByIndex(i)).getTraffic());
            }
            if (repository.getByIndex(i) instanceof DigitalTelevision) {
                LOGGER.info("Channel package: {}", ((DigitalTelevision) repository.getByIndex(i)).getChannelPackage());
            }
            if (repository.getByIndex(i) instanceof WiredInternet) {
                LOGGER.info("Connection speed: {}", ((WiredInternet) repository.getByIndex(i)).getConnectionSpeed());
            }

            int expectedSize = 5;
            assertEquals(expectedSize, repository.getSize());
        }
    }
}