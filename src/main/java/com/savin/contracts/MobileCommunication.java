package com.savin.contracts;

import com.savin.entities.Person;

import java.time.LocalDate;

/**
 * This class represents mobile communication contract model in a system
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class MobileCommunication extends Contract{

    /**
     * Number of minutes according to the tariff
     */
    private int minutes;

    /**
     * Number of SMS according to the tariff
     */
    private int sms;

    /**
     * GBytes of Internet traffic according to the tariff
     */
    private double traffic;

    /**
     * @return number of minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * @return number of SMS
     */
    public int getSms() {
        return sms;
    }

    /**
     * @return amount of Internet traffic (in GBytes)
     */
    public double getTraffic() {
        return traffic;
    }

    /**
     * Creates a new Mobile Communication Contract with the given ID, contract start date, contract end date,
     * contract number, contract holder, number of minutes, number of SMS and amount of Internet traffic
     *
     * @param ID set the initial value for the class attribute ID
     * @param startDate contract start date to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param endDate contract end date to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param contractNumber contract number to set
     * @param contractHolder contract holder to set
     * @param minutes number of minutes to set
     * @param sms number of SMS to set
     * @param traffic number of GBytes to set
     */
    public MobileCommunication(int ID, LocalDate startDate, LocalDate endDate, int contractNumber, Person contractHolder,
                        int minutes, int sms, double traffic) {
        super(ID, startDate, endDate, contractNumber, contractHolder);
        this.minutes = minutes;
        this.sms = sms;
        this.traffic = traffic;
    }
}
