package com.savin.contracts;

import com.savin.entities.Person;
import com.savin.enums.ChannelPackage;

import java.time.LocalDate;

/**
 * This class represents digital TV contract model in a system
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class DigitalTelevision extends Contract {

    /**
     * A package with TV channels
     */
    private ChannelPackage channelPackage;

    /**
     * @return selected channel package
     */
    public ChannelPackage getChannelPackage() {
        return channelPackage;
    }

    /**
     * Creates a new Digital TV Contract with the given ID, contract start date, contract end date,
     * contract number, contract holder and selected channel package.
     *
     * @param ID set the initial value for the class attribute ID
     * @param startDate contract start date to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param endDate contract end date to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param contractNumber contract number to set
     * @param contractHolder contract holder to set
     * @param channelPackage platform channel package to set
     */
    public DigitalTelevision(int ID, LocalDate startDate, LocalDate endDate, int contractNumber, Person contractHolder,
                      ChannelPackage channelPackage) {
        super(ID, startDate, endDate, contractNumber, contractHolder);
        this.channelPackage = channelPackage;
    }
}
