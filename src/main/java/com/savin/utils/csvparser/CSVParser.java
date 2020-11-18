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
import com.savin.repository.core.Repository;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
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
     * A List of persons from CSV file
     */
    List<Person> persons = new ArrayList<>();

    /**
     * The person whose contract is currently being parsed
     */
    Person currentPerson;

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

            for (CSVContract csvContract : csvToBean) {
                for (Person p : persons) {
                    if ((p.getID() != csvContract.getContractHolderID()) &&
                            (!p.getFullName().equals(csvContract.getFullName())) &&
                            (!p.getGender().equals(csvContract.getGender())) &&
                            (!p.getPassportDetails().equals(csvContract.getPassportDetails()))) {
                        persons.add(new Person(csvContract.getContractHolderID(),
                                csvContract.getFullName(), csvContract.getBirthDate(),
                                csvContract.getGender(), csvContract.getPassportDetails()));
                    }
                }

                for (Person p : persons) {
                    if ((p.getID() == csvContract.getContractHolderID()) &&
                            (p.getFullName().equals(csvContract.getFullName())) &&
                            (p.getGender().equals(csvContract.getGender())) &&
                            (p.getPassportDetails().equals(csvContract.getPassportDetails()))) {
                        currentPerson = p;
                    }
                }

                if (csvContract.getContractType().equals(ContractType.TV)) {
                    repository.add(parseDigitalTelevision(csvContract));
                }

                if (csvContract.getContractType().equals(ContractType.MOBILE)) {
                    repository.add(parseMobileCommunication(csvContract));
                }

                if (csvContract.getContractType().equals(ContractType.INTERNET)) {
                    repository.add(parseWiredInternet(csvContract));
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
        ChannelPackage channelPackage;

        if (csvContract.getAdditionalInfo().toString().toUpperCase().equals("ENTERTAINMENT")) {
            channelPackage = ChannelPackage.ENTERTAINMENT;
        }
        if (csvContract.getAdditionalInfo().toString().toUpperCase().equals("ULTIMATE")) {
            channelPackage = ChannelPackage.ULTIMATE;
        }
        if (csvContract.getAdditionalInfo().toString().toUpperCase().equals("SPORTS")) {
            channelPackage = ChannelPackage.SPORTS;
        }
        else {
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
