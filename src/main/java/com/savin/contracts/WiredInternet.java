package com.savin.contracts;

import com.savin.entities.Person;

import java.time.LocalDate;

/**
 * This class represents wired Internet contract model in a system
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class WiredInternet extends Contract {

    /**
     * Connection speed according to the tariff (in Mbps)
     */
    private double connectionSpeed;

    /**
     * @return Internet connection speed (in Mbps)
     */
    public double getConnectionSpeed() {
        return connectionSpeed;
    }

    /**
     * @param connectionSpeed Internet connection speed to set
     */
    public void setConnectionSpeed(double connectionSpeed) {
        this.connectionSpeed = connectionSpeed;
    }

    /**
     * Creates a new Wired Internet Contract with the given ID, contract start date, contract end date,
     * contract number, contract holder and Internet connection speed
     *
     * @param ID set the initial value for the class attribute ID
     * @param startDate contract start date to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param endDate contract end date to set (a date without a time-zone in the ISO-8601 calendar system)
     * @param contractNumber contract number to set
     * @param contractHolder contract holder to set
     * @param connectionSpeed Internet connection speed to set
     */
    public WiredInternet(int ID, LocalDate startDate, LocalDate endDate, int contractNumber, Person contractHolder,
                  double connectionSpeed) {
        super(ID, startDate, endDate, contractNumber, contractHolder);
        this.connectionSpeed = connectionSpeed;
    }
}
