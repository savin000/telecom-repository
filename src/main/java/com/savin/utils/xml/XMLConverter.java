package com.savin.utils.xml;

import com.savin.contracts.Contract;
import com.savin.contracts.DigitalTelevision;
import com.savin.contracts.MobileCommunication;
import com.savin.contracts.WiredInternet;
import com.savin.repository.core.ContractRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.time.LocalDate;

public class XMLConverter {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PATH = "contracts.xml";

    public void convertToXml(ContractRepository contract) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ContractRepository.class, Contract.class,
                    WiredInternet.class, DigitalTelevision.class, MobileCommunication.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(contract, new File(PATH));
        } catch(JAXBException e) {
            LOGGER.error("An error was encountered while creating the JAXBContext", e);
        }
    }
}
