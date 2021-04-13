package com.savin.utils.xml;

import com.savin.contracts.Contract;
import com.savin.contracts.DigitalTelevision;
import com.savin.contracts.MobileCommunication;
import com.savin.contracts.WiredInternet;
import com.savin.entities.Person;
import com.savin.repository.core.ContractRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * XML converter class using JAXB
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class XMLConverter {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Convert repository of contracts to XML using JAXB marshaller
     *
     * @param contractRepository the repository to be converted to XML
     * @param pathToXML the path to the XML file
     */
    public static void convertToXml(ContractRepository contractRepository, String pathToXML) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ContractRepository.class, Contract.class,
                    WiredInternet.class, DigitalTelevision.class, MobileCommunication.class, Person.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(contractRepository, new File(pathToXML));
        } catch(JAXBException e) {
            LOGGER.error("An error was encountered while creating the JAXBContext", e);
        }
    }

    /**
     * Convert XML to repository of contracts using JAXB unmarshaller
     *
     * @param pathToXML the path to the XML file
     * @return repository with contracts
     */
    public static ContractRepository convertToObject(String pathToXML) {
        ContractRepository contractRepository = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ContractRepository.class, Contract.class,
                    WiredInternet.class, DigitalTelevision.class, MobileCommunication.class, Person.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            contractRepository = (ContractRepository) unmarshaller.unmarshal(new FileReader(pathToXML));
        } catch (JAXBException e) {
            LOGGER.error("An error was encountered while creating the JAXBContext", e);
        } catch (FileNotFoundException e) {
            LOGGER.error("An error was encountered while reading the file", e);
        }
        return contractRepository;
    }
}
