package com.savin.contracts;

import com.savin.entities.Person;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

/**
 * This class represents mobile communication contract model in a system
 *
 * @author Mikhail Savin
 * @since 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MobileCommunication extends Contract{

    /**
     * Number of minutes according to the tariff
     */
    @XmlElement(name = "minutes")
    private int minutes;

    /**
     * Number of SMS according to the tariff
     */
    @XmlElement(name = "sms")
    private int sms;

    /**
     * GBytes of Internet traffic according to the tariff
     */
    @XmlElement(name = "traffic")
    private double traffic;

    /**
     * @return number of minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * @param minutes number of minutes to set
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * @return number of SMS
     */
    public int getSms() {
        return sms;
    }

    /**
     * @param sms number of SMS to set
     */
    public void setSms(int sms) {
        this.sms = sms;
    }

    /**
     * @return amount of Internet traffic (in GBytes)
     */
    public double getTraffic() {
        return traffic;
    }

    /**
     * @param traffic number of GBytes to set
     */
    public void setTraffic(double traffic) {
        this.traffic = traffic;
    }

    /**
     * Default constructor (added as it is used by JAXB)
     */
    public MobileCommunication() {
        super();
    }

    /**
     * Creates a new Mobile Communication Contract with the given id, contract start date, contract end date,
     * contract number, contract holder, number of minutes, number of SMS and amount of Internet traffic
     *
     * @param id set the initial value for the class attribute id
     * @param startDate contract start date to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param endDate contract end date to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param contractNumber contract number to set
     * @param contractHolder contract holder to set
     * @param minutes number of minutes to set
     * @param sms number of SMS to set
     * @param traffic number of GBytes to set
     */
    public MobileCommunication(int id, LocalDate startDate, LocalDate endDate, int contractNumber, Person contractHolder,
                        int minutes, int sms, double traffic) {
        super(id, startDate, endDate, contractNumber, contractHolder);
        this.minutes = minutes;
        this.sms = sms;
        this.traffic = traffic;
    }
}
