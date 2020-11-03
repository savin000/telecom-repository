package com.savin.contracts;

import com.savin.entities.Person;

import java.time.LocalDate;

/**
 * This class is used as a superclass for all the contracts in a system.
 * It is abstract because we need only particular contracts to be created.
 *
 * @author Mikhail Savin
 * @see DigitalTelevision
 * @see MobileCommunication
 * @see WiredInternet
 * @since 1.0
 */
public abstract class Contract {

    /**
     * A number which uniquely identifies a contract
     */
    private int ID;

    /**
     * A date when the contract was concluded
     */
    private LocalDate startDate;

    /**
     * A date when the contract ends
     */
    private LocalDate endDate;

    /**
     * The number assigned to the contract
     */
    private int contractNumber;

    /**
     * A person who holds this specific contract
     */
    private Person contractHolder;

    /**
     * @return this contract's ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return contract start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate contract start date to set (a date without a time-zone in the ISO-8601 calendar system)
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return contract end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @param endDate contract end date to set (a date without a time-zone in the ISO-8601 calendar system)
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * @return contract number
     */
    public int getContractNumber() {
        return contractNumber;
    }

    /**
     * @param contractNumber contract number to set
     */
    public void setContractNumber(int contractNumber) {
        this.contractNumber = contractNumber;
    }

    /**
     * @return person who holds this contract
     */
    public Person getContractHolder() {
        return contractHolder;
    }

    /**
     * @param contractHolder contract holder to set
     */
    public void setContractHolder(Person contractHolder) {
        this.contractHolder = contractHolder;
    }

    /**
     * Creates a new Contract with the given ID, contract start date, contract end date,
     * contract number and contract holder.
     *
     * @param ID set the initial value for the class attribute ID
     * @param startDate contract start date to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param endDate contract end date to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param contractNumber contract number to set
     * @param contractHolder contract holder to set
     */
    public Contract(int ID, LocalDate startDate, LocalDate endDate, int contractNumber, Person contractHolder) {
        this.ID = ID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractNumber = contractNumber;
        this.contractHolder = contractHolder;
    }
}
