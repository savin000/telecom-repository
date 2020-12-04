package com.savin.utils.validation.validators;

import com.savin.contracts.Contract;
import com.savin.contracts.NoBirthDateException;
import com.savin.enums.ValidationStatus;
import com.savin.utils.validation.ValidationInfo;

/**
 * This class is used to validate age of the contract holder.
 * It must be greater than 18
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class AgeValidator implements Validator<Contract>{

    /**
     * This method validates given contract by contract holder's age
     *
     * @param contract person's(contract holder's) contract. So we can get information about the person himself
     * @return result of the validation
     */
    @Override
    public ValidationInfo validate(Contract contract) {
        try {
            if (contract.getContractHolder().getAge() < 18) {
                return new ValidationInfo(ValidationStatus.WARNING, "Person is under 18 years of age");
            }
        } catch (NoBirthDateException e) {
            return new ValidationInfo(ValidationStatus.ERROR, "No date of birth");
        }
        return new ValidationInfo(ValidationStatus.FINE, "Person is 18 years of age or over");
    }
}
