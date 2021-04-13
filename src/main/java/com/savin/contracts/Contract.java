package com.savin.contracts;

import com.savin.entities.Person;
import com.savin.utils.xml.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Contract {

    /**
     * A number which uniquely identifies a contract
     */
    @XmlElement(name = "id")
    private int id;

    /**
     * A date when the contract was concluded
     */
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @XmlElement(name = "startDate")
    private LocalDate startDate;

    /**
     * A date when the contract ends
     */
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @XmlElement(name = "endDate")
    private LocalDate endDate;

    /**
     * The number assigned to the contract
     */
    @XmlElement(name = "contractNumber")
    private int contractNumber;

    /**
     * A person who holds this specific contract
     */
    @XmlElement(name = "contractHolder")
    private Person contractHolder;

    /**
     * @return this contract's id
     */
    public int getID() {
        return id;
    }

    /**
     * @param id id to set
     */
    public void setID(int id) {
        this.id = id;
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
     * Default constructor (added as it is used by JAXB)
     */
    public Contract() {
    }

    /**
     * Creates a new Contract with the given ID, contract start date, contract end date,
     * contract number and contract holder.
     *
     * @param id set the initial value for the class attribute ID
     * @param startDate contract start date to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param endDate contract end date to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param contractNumber contract number to set
     * @param contractHolder contract holder to set
     */
    public Contract(int id, LocalDate startDate, LocalDate endDate, int contractNumber, Person contractHolder) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractNumber = contractNumber;
        this.contractHolder = contractHolder;
    }
}
