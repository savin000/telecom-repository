package com.savin.contracts;

import com.savin.entities.Person;
import com.savin.enums.ChannelPackage;

import java.time.LocalDate;

public class DigitalTelevision extends Contract {
    private ChannelPackage channelPackage;

    public DigitalTelevision(int ID, LocalDate startDate, LocalDate endDate, int contractNumber, Person contractHolder,
                      ChannelPackage channelPackage) {
        super(ID, startDate, endDate, contractNumber, contractHolder);
        this.channelPackage = channelPackage;
    }
}
