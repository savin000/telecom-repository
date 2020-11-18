package com.savin.utils.csvparser;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.savin.enums.ContractType;

import java.time.LocalDate;

/**
 * This class represents contract model, which is used to parse CSV data
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class CSVContract {
    /**
     * A number which identifies a contract
     */
    @CsvBindByName(column = "ContractID")
    private int contractID;

    /**
     * A date when the contract was concluded
     */
    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "ContractStartDate")
    private LocalDate contractStartDate;

    /**
     * A date when the contract ends
     */
    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "ContractEndDate")
    private LocalDate contractEndDate;

    /**
     * The number assigned to the contract
     */
    @CsvBindByName(column = "ContractNumber")
    private int contractNumber;

    /**
     * A number which identifies a contract holder
     */
    @CsvBindByName(column = "ContractHolderID")
    private int contractHolderID;

    /**
     * Full name of a contract holder
     */
    @CsvBindByName(column = "FullName")
    private String fullName;

    /**
     * Contract holder's date of birth
     */
    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "BirthDate")
    private LocalDate birthDate;

    /**
     * Contract holder's gender (may not be specified)
     */
    @CsvBindByName(column = "Gender")
    private String gender;

    /**
     * Contract holder's passport details
     */
    @CsvBindByName(column = "PassportDetails")
    private String passportDetails;

    /**
     * A type of a contract
     */
    @CsvBindByName(column = "ContractType")
    private ContractType contractType;

    /**
     * Additional information about a contract
     */
    @CsvBindByName(column = "AdditionalInfo")
    private Object additionalInfo;

    /**
     * @return contract's ID
     */
    public int getContractID() {
        return contractID;
    }

    /**
     * @return contract start date
     */
    public LocalDate getContractStartDate() {
        return contractStartDate;
    }

    /**
     * @return contract end date
     */
    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    /**
     * @return contract number
     */
    public int getContractNumber() {
        return contractNumber;
    }

    /**
     * @return contract holder's ID
     */
    public int getContractHolderID() {
        return contractHolderID;
    }

    /**
     * @return contract holder's full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @return contract holder's date of birth
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * @return contract holder's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return contract holder's passport details
     */
    public String getPassportDetails() {
        return passportDetails;
    }

    /**
     * @return type of the contract
     */
    public ContractType getContractType() {
        return contractType;
    }

    /**
     * @return additional information about a contract
     */
    public Object getAdditionalInfo() {
        return additionalInfo;
    }
}
