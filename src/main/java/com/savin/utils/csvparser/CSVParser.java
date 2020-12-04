package com.savin.utils.csvparser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.savin.contracts.Contract;
import com.savin.contracts.DigitalTelevision;
import com.savin.contracts.MobileCommunication;
import com.savin.contracts.WiredInternet;
import com.savin.entities.Person;
import com.savin.enums.ChannelPackage;
import com.savin.enums.ContractType;
import com.savin.enums.ValidationStatus;
import com.savin.repository.core.Repository;
import com.savin.utils.validation.validators.AgeValidator;
import com.savin.utils.validation.validators.ContractEndDateValidator;
import com.savin.utils.validation.validators.ContractNumberValidator;
import com.savin.utils.validation.validators.Validator;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is needed to parse CSV
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class CSVParser {

    /**
     * A list that contains all validators for CSV parser
     */
    private static List<Validator<Contract>> validators = new ArrayList<>();
    static {
        validators.add(new AgeValidator());
        validators.add(new ContractNumberValidator(3));
        validators.add(new ContractEndDateValidator(LocalDate.of(2015, 1, 1)));
    }

    /**
     * A List of persons from CSV file
     */
    private List<Person> persons = new ArrayList<>();

    /**
     * The person whose contract is currently being parsed
     */
    private Person currentPerson;

    /**
     * This method parses data from CSV to repository
     * @param repository the repository where the parsing results are written
     * @param CSVFile the file where the data for parsing comes from
     * @throws IOException if stream to file cannot be read to or closed.
     */
    public void parse(Repository<Contract> repository, String CSVFile) throws IOException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(CSVFile))
        ) {
            CsvToBean<CSVContract> csvToBean = new CsvToBeanBuilder<CSVContract>(reader)
                    .withType(CSVContract.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            // can't iterate over empty list, so we need to add a Person
            persons.add(new Person(0,"", null, "", ""));

            for (CSVContract csvContract : csvToBean) {
                boolean add = false;

                // check if we already have this person
                for(Person p : persons) {
                    if ((p.getID() != csvContract.getContractHolderID()) &&
                            (!p.getFullName().equals(csvContract.getFullName())) &&
                            (!p.getGender().equals(csvContract.getGender())) &&
                            (!p.getPassportDetails().equals(csvContract.getPassportDetails()))) {
                        add = true;
                        break;
                    }
                }

                // if we have a new person, we add him to the list of persons
                if (add) {
                    persons.add(new Person(csvContract.getContractHolderID(),
                            csvContract.getFullName(), csvContract.getBirthDate(),
                            csvContract.getGender(), csvContract.getPassportDetails()));
                }

                for (Person p : persons) {
                    if ((p.getID() == csvContract.getContractHolderID()) &&
                            (p.getFullName().equals(csvContract.getFullName())) &&
                            (p.getGender().equals(csvContract.getGender())) &&
                            (p.getPassportDetails().equals(csvContract.getPassportDetails()))) {
                        currentPerson = p;
                    }
                }

                int passedValidatorsNumber = 0;

                if (csvContract.getContractType().equals(ContractType.TV)) {
                   DigitalTelevision contract = parseDigitalTelevision(csvContract);

                   for (Validator<Contract> validator : validators) {
                       if (validator.validate(contract).getStatus() == ValidationStatus.FINE) {
                           passedValidatorsNumber = passedValidatorsNumber + 1;
                       }
                   }
                   if (passedValidatorsNumber == validators.size()) {
                       repository.add(contract);
                   }
                   passedValidatorsNumber = 0;
                }

                if (csvContract.getContractType().equals(ContractType.MOBILE)) {
                    MobileCommunication contract = parseMobileCommunication(csvContract);

                    for (Validator<Contract> validator : validators) {
                        if (validator.validate(contract).getStatus() == ValidationStatus.FINE) {
                            passedValidatorsNumber = passedValidatorsNumber + 1;
                        }
                    }
                    if (passedValidatorsNumber == validators.size()) {
                        repository.add(contract);
                    }
                    passedValidatorsNumber = 0;
                }

                if (csvContract.getContractType().equals(ContractType.INTERNET)) {
                    WiredInternet contract = parseWiredInternet(csvContract);

                    for (Validator<Contract> validator : validators) {
                        if (validator.validate(contract).getStatus() == ValidationStatus.FINE) {
                            passedValidatorsNumber = passedValidatorsNumber + 1;
                        }
                    }
                    if (passedValidatorsNumber == validators.size()) {
                        repository.add(contract);
                    }
                }
            }
        }
    }

    /**
     * Parsing contract data to get instance of DigitalTelevision class
     * @param csvContract contract data retrieved from CSV file
     * @return Digital Television contract
     */
    private DigitalTelevision parseDigitalTelevision(CSVContract csvContract) {
        ChannelPackage channelPackage = ChannelPackage.BASIC;

        if (csvContract.getAdditionalInfo().toString().toUpperCase().equals("ENTERTAINMENT")) {
            channelPackage = ChannelPackage.ENTERTAINMENT;
        }
        if (csvContract.getAdditionalInfo().toString().toUpperCase().equals("ULTIMATE")) {
            channelPackage = ChannelPackage.ULTIMATE;
        }
        if (csvContract.getAdditionalInfo().toString().toUpperCase().equals("SPORTS")) {
            channelPackage = ChannelPackage.SPORTS;
        }
        if (csvContract.getAdditionalInfo().toString().toUpperCase().equals("BASIC")) {
            channelPackage = ChannelPackage.BASIC;
        }

        return new DigitalTelevision(csvContract.getContractID(), csvContract.getContractStartDate(),
                csvContract.getContractEndDate(), csvContract.getContractNumber(),
                currentPerson, channelPackage);
    }

    /**
     * Parsing contract data to get instance of MobileCommunication class
     * @param csvContract contract data retrieved from CSV file
     * @return Mobile Communication contract
     */
    private MobileCommunication parseMobileCommunication(CSVContract csvContract) {
        String additionalInfo = csvContract.getAdditionalInfo().toString();
        String minutes = additionalInfo.substring(0, additionalInfo.indexOf("/"));
        additionalInfo = additionalInfo.substring(minutes.length() + 1);
        String sms = additionalInfo.substring(0, additionalInfo.indexOf("/"));
        additionalInfo = additionalInfo.substring(sms.length() + 1);
        String traffic = additionalInfo;

        return new MobileCommunication(csvContract.getContractID(), csvContract.getContractStartDate(),
                csvContract.getContractEndDate(), csvContract.getContractNumber(),
                currentPerson, Integer.parseInt(minutes), Integer.parseInt(sms), Double.parseDouble(traffic));
    }

    /**
     * Parsing contract data to get instance of WiredInternet class
     * @param csvContract contract data retrieved from CSV file
     * @return Wired Internet contract
     */
    private WiredInternet parseWiredInternet(CSVContract csvContract) {
        return new WiredInternet(csvContract.getContractID(), csvContract.getContractStartDate(),
                csvContract.getContractEndDate(), csvContract.getContractNumber(),
                currentPerson, Double.parseDouble(csvContract.getAdditionalInfo().toString()));
    }
}
