package com.savin.utils.validation.validators;

import com.savin.contracts.Contract;
import com.savin.enums.ValidationStatus;
import com.savin.utils.validation.ValidationInfo;

/**
 * This class is used to validate number of contracts a person holds
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class ContractNumberValidator implements Validator<Contract> {

    /**
     * Allowed number of contracts
     */
    private int contractNumberMaxValue;

    /**
     * @return maximum (allowed) number of contracts
     */
    public int getContractNumberMaxValue() {
        return contractNumberMaxValue;
    }

    /**
     * @param contractNumberMaxValue allowed number of contracts to set
     */
    public void setContractNumberMaxValue(int contractNumberMaxValue) {
        this.contractNumberMaxValue = contractNumberMaxValue;
    }

    /**
     * Creates a new validator to validate number of contracts a person has
     *
     * @param contractNumberMaxValue allowed number of contracts to set
     */
    public ContractNumberValidator(int contractNumberMaxValue) {
        this.contractNumberMaxValue = contractNumberMaxValue;
    }

    /**
     * This method validates given contract by number of contracts
     *
     * @param contract person's(contract holder's) contract. So we can get information about the contract number
     * @return result of the validation
     */
    @Override
    public ValidationInfo validate(Contract contract) {
        if (contract.getContractNumber() <= contractNumberMaxValue) {
            return new ValidationInfo(ValidationStatus.FINE,
                    "Contract number is lower than " + contractNumberMaxValue);
        }
        else {
            return new ValidationInfo(ValidationStatus.ERROR,
                    "A person can not hold more than " + contractNumberMaxValue + " contracts!");
        }
    }
}
