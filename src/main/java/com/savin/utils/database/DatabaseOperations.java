package com.savin.utils.database;

import com.savin.contracts.Contract;
import com.savin.contracts.DigitalTelevision;
import com.savin.contracts.MobileCommunication;
import com.savin.contracts.WiredInternet;
import com.savin.entities.Person;
import com.savin.enums.ChannelPackage;
import com.savin.repository.core.ContractRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class allows to save repository with contracts to database or
 * retrieve data from the database
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class DatabaseOperations {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * SQL query to create table for persons
     */
    private static final String CREATE_PERSON_TABLE = "CREATE TABLE IF NOT EXISTS person(" +
            "ID INT PRIMARY KEY, FULL_NAME VARCHAR(255), BIRTH_DATE DATE," +
            "GENDER VARCHAR(255), PASSPORT_DETAILS VARCHAR(255))";

    /**
     * SQL query to create table for contracts
     */
    private static final String CREATE_CONTRACT_TABLE = "CREATE TABLE IF NOT EXISTS contract(" +
            "CONTRACT_ID INT PRIMARY KEY, CONTRACT_START_DATE DATE, CONTRACT_END_DATE DATE," +
            "CONTRACT_NUMBER INT, CONTRACT_TYPE VARCHAR(255), ADDITIONAL_INFO VARCHAR(255), CONTRACT_HOLDER_ID INT," +
            "foreign key (CONTRACT_HOLDER_ID) references person(ID))";

    /**
     * SQL query to insert person into 'person' table
     */
    private static final String INSERT_PERSON = "INSERT INTO person VALUES(?, ?, ?, ?, ?)";

    /**
     * SQL query to insert contract into 'contract' table
     */
    private static final String INSERT_CONTRACT = "INSERT INTO contract VALUES(?, ?, ?, ?, ?, ?, ?)";

    /**
     * SQL query to get contract with a person who holds it
     */
    private static final String SELECT_REPOSITORY = "SELECT c.*, p.* FROM contract AS c JOIN person AS p ON c.contract_holder_id = p.id";

    /**
     * Database driver
     */
    private String driver;

    /**
     * Database url
     */
    private String url;

    /**
     * Database user
     */
    private String user;

    /**
     * Database user's password
     */
    private String password;

    /**
     * Sets database driver and loads connection properties
     */
    public DatabaseOperations() {
        loadProperties();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class was not found", e);
        }
    }

    /**
     * Loads database connection properties from resources and initializes corresponding fields
     */
    private void loadProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream = DatabaseOperations.class.getResourceAsStream("/connection.properties")) {
            properties.load(inputStream);

            driver = properties.getProperty(DatabaseOperationsConstants.DRIVER_PROPERTY_NAME);
            url = properties.getProperty(DatabaseOperationsConstants.URL_PROPERTY_NAME);
            user = properties.getProperty(DatabaseOperationsConstants.USER_PROPERTY_NAME);
            password = properties.getProperty(DatabaseOperationsConstants.PASSWORD_PROPERTY_NAME);
        } catch (IOException e) {
            LOGGER.error("Something went wrong while getting properties", e);
        }
    }

    /**
     * This method creates a table for persons using SQL query (if it doesn't exist)
     */
    public void createPersonTableIfNotExists() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_PERSON_TABLE);
        } catch (SQLException e) {
            LOGGER.error("Database access error or the url is null", e);
        }
    }

    /**
     * This method creates a table for contracts using SQL query (if it doesn't exist)
     */
    public void createContractTableIfNotExists() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_CONTRACT_TABLE);
        } catch (SQLException e) {
            LOGGER.error("Database access error or the url is null", e);
        }
    }

    /**
     * Saves repository to the database using SQL insert query.
     * Person is saved to 'PERSON' table.
     * Contract is saved to 'CONTRACT' table with a person's foreign key.
     *
     * @param repository the repository with the data you want to save
     */
    public void saveContractRepository(ContractRepository repository) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement personStatement = connection.prepareStatement(INSERT_PERSON);
             PreparedStatement contractStatement = connection.prepareStatement(INSERT_CONTRACT)) {

            List<Person> persons = new ArrayList<>();
            boolean add;

            for (int i = 0; i < repository.getSize(); i++) {
                add = true;
                Person currentPerson = repository.getByIndex(i).getContractHolder();

                for (Person p : persons) {
                    if (currentPerson.equals(p)) {
                        add = false;
                        break;
                    }
                }

                if (add) {
                    persons.add(currentPerson);
                    personStatement.setInt(1, currentPerson.getID());
                    personStatement.setString(2, currentPerson.getFullName());
                    personStatement.setDate(3, Date.valueOf(currentPerson.getBirthDate()));
                    personStatement.setString(4, currentPerson.getGender());
                    personStatement.setString(5, currentPerson.getPassportDetails());
                    personStatement.executeUpdate();
                }
            }

            for (int i = 0; i < repository.getSize(); i++) {
                Contract currentContract = repository.getByIndex(i);

                contractStatement.setInt(1, currentContract.getID());
                contractStatement.setDate(2, Date.valueOf(currentContract.getStartDate()));
                contractStatement.setDate(3, Date.valueOf(currentContract.getEndDate()));
                contractStatement.setInt(4, currentContract.getContractNumber());
                if (currentContract instanceof DigitalTelevision) {
                    contractStatement.setString(5, "TV");
                    contractStatement.setString(6,
                            String.valueOf(((DigitalTelevision) currentContract).getChannelPackage()));
                } else if (currentContract instanceof MobileCommunication) {
                    contractStatement.setString(5, "Mobile");
                    String additionalInfo = ((MobileCommunication) currentContract).getMinutes() + "/" +
                            ((MobileCommunication) currentContract).getSms() + "/" +
                            ((MobileCommunication) currentContract).getTraffic();
                    contractStatement.setString(6, additionalInfo);
                } else if (currentContract instanceof WiredInternet) {
                    contractStatement.setString(5, "Internet");
                    contractStatement.setDouble(6, ((WiredInternet) currentContract).getConnectionSpeed());
                }
                contractStatement.setInt(7, currentContract.getContractHolder().getID());
                contractStatement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("Database access error or the url is null", e);
        }
    }

    /**
     * Restores a repository from database
     *
     * @return repository with the data from database
     */
    public ContractRepository getContractRepository() {
        ContractRepository repository = new ContractRepository();
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_REPOSITORY);
            while (resultSet.next()) {
                Person person = new Person(
                        resultSet.getInt("ID"),
                        resultSet.getString("FULL_NAME"),
                        resultSet.getDate("BIRTH_DATE").toLocalDate(),
                        resultSet.getString("GENDER"),
                        resultSet.getString("PASSPORT_DETAILS"));
                switch (resultSet.getString("CONTRACT_TYPE")) {
                    case "TV": {
                        Contract contract = new DigitalTelevision(
                                resultSet.getInt("CONTRACT_ID"),
                                resultSet.getDate("CONTRACT_START_DATE").toLocalDate(),
                                resultSet.getDate("CONTRACT_END_DATE").toLocalDate(),
                                resultSet.getInt("CONTRACT_NUMBER"),
                                person,
                                ChannelPackage.valueOf(resultSet.getString("ADDITIONAL_INFO")));
                        repository.add(contract);
                        break;
                    }
                    case "Mobile": {
                        String additionalInfo = resultSet.getString("ADDITIONAL_INFO");
                        String minutes = additionalInfo.substring(0, additionalInfo.indexOf("/"));
                        additionalInfo = additionalInfo.substring(minutes.length() + 1);
                        String sms = additionalInfo.substring(0, additionalInfo.indexOf("/"));
                        additionalInfo = additionalInfo.substring(sms.length() + 1);
                        String traffic = additionalInfo;

                        Contract contract = new MobileCommunication(
                                resultSet.getInt("CONTRACT_ID"),
                                resultSet.getDate("CONTRACT_START_DATE").toLocalDate(),
                                resultSet.getDate("CONTRACT_END_DATE").toLocalDate(),
                                resultSet.getInt("CONTRACT_NUMBER"),
                                person,
                                Integer.parseInt(minutes),
                                Integer.parseInt(sms),
                                Double.parseDouble(traffic)
                        );
                        repository.add(contract);
                        break;
                    }
                    case "Internet": {
                        Contract contract = new WiredInternet(
                                resultSet.getInt("CONTRACT_ID"),
                                resultSet.getDate("CONTRACT_START_DATE").toLocalDate(),
                                resultSet.getDate("CONTRACT_END_DATE").toLocalDate(),
                                resultSet.getInt("CONTRACT_NUMBER"),
                                person,
                                resultSet.getDouble("ADDITIONAL_INFO"));
                        repository.add(contract);
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Database access error or the url is null", e);
        }
        return repository;
    }
}
