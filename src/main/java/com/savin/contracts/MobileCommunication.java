package com.savin.contracts;

import com.savin.entities.Person;

import java.time.LocalDate;

public class MobileCommunication extends Contract{
    private int minutes;
    private int sms;
    private double traffic; // GB

    public int getMinutes() {
        return minutes;
    }

    public int getSms() {
        return sms;
    }

    public double getTraffic() {
        return traffic;
    }

    public MobileCommunication(int ID, LocalDate startDate, LocalDate endDate, int contractNumber, Person contractHolder,
                        int minutes, int sms, double traffic) {
        super(ID, startDate, endDate, contractNumber, contractHolder);
        this.minutes = minutes;
        this.sms = sms;
        this.traffic = traffic;
    }
}
