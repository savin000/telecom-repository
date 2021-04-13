package com.savin.contracts;

import com.savin.entities.Person;
import com.savin.enums.ChannelPackage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

/**
 * This class represents digital TV contract model in a system
 *
 * @author Mikhail Savin
 * @since 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DigitalTelevision extends Contract {

    /**
     * A package with TV channels
     */
    @XmlElement(name = "channelPackage")
    private ChannelPackage channelPackage;

    /**
     * @return selected channel package
     */
    public ChannelPackage getChannelPackage() {
        return channelPackage;
    }

    /**
     * @param channelPackage channel package to set
     */
    public void setChannelPackage(ChannelPackage channelPackage) {
        this.channelPackage = channelPackage;
    }

    /**
     * Default constructor (added as it is used by JAXB)
     */
    public DigitalTelevision() {
        super();
    }

    /**
     * Creates a new Digital TV Contract with the given id, contract start date, contract end date,
     * contract number, contract holder and selected channel package.
     *
     * @param id set the initial value for the class attribute id
     * @param startDate contract start date to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param endDate contract end date to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param contractNumber contract number to set
     * @param contractHolder contract holder to set
     * @param channelPackage platform channel package to set
     */
    public DigitalTelevision(int id, LocalDate startDate, LocalDate endDate, int contractNumber, Person contractHolder,
                      ChannelPackage channelPackage) {
        super(id, startDate, endDate, contractNumber, contractHolder);
        this.channelPackage = channelPackage;
    }
}
