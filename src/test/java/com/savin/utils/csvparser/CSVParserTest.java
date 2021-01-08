package com.savin.utils.csvparser;

import com.savin.contracts.DigitalTelevision;
import com.savin.contracts.MobileCommunication;
import com.savin.di.Injector;
import com.savin.di.NoInjectableClassesException;
import com.savin.di.SurplusOfInjectableClassesException;
import com.savin.entities.NoBirthDateException;
import com.savin.contracts.WiredInternet;
import com.savin.repository.core.ContractRepository;
import org.junit.Test;

import java.io.IOException;

public class CSVParserTest {

    @Test
    public void test() throws IOException, NoBirthDateException, NoInjectableClassesException, InstantiationException, SurplusOfInjectableClassesException, IllegalAccessException {
        String path = "/home/mikhail/TelecomDataExample.csv";
        ContractRepository repository = new ContractRepository();
        CSVParser parser = new CSVParser();
        Injector.inject(parser);
        parser.parse(repository, path);

        for (int i = 0; i < repository.getSize(); i++) {
            System.out.println("ID "+ repository.getByIndex(i).getID());
            System.out.println("Start date " + repository.getByIndex(i).getStartDate());
            System.out.println("End date " + repository.getByIndex(i).getEndDate());
            System.out.println("Contract number " + repository.getByIndex(i).getContractNumber());
            System.out.println("Contract holder's ID " + repository.getByIndex(i).getContractHolder().getID());
            System.out.println("Contract holder's full name " + repository.getByIndex(i).getContractHolder().getFullName());
            System.out.println("Contract holder's age " + repository.getByIndex(i).getContractHolder().getAge());
            System.out.println("Contract holder's gender " + repository.getByIndex(i).getContractHolder().getGender());
            System.out.println("Contract holder's passport details " + repository.getByIndex(i).getContractHolder().getPassportDetails());

            if (repository.getByIndex(i) instanceof MobileCommunication) {
                System.out.println("Minutes: " + ((MobileCommunication) repository.getByIndex(i)).getMinutes());
                System.out.println("SMS: " + ((MobileCommunication) repository.getByIndex(i)).getSms());
                System.out.println("Traffic (GB): " + ((MobileCommunication) repository.getByIndex(i)).getTraffic());
            }
            if (repository.getByIndex(i) instanceof DigitalTelevision) {
                System.out.println("Channel package: " + ((DigitalTelevision) repository.getByIndex(i)).getChannelPackage());
            }
            if (repository.getByIndex(i) instanceof WiredInternet) {
                System.out.println("Connection speed: " + ((WiredInternet) repository.getByIndex(i)).getConnectionSpeed());
            }
        }
    }
}