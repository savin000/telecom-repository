package com.savin.utils.validation.validators;

import com.savin.contracts.Contract;
import com.savin.contracts.NoBirthDateException;
import com.savin.enums.ValidationStatus;
import com.savin.utils.validation.ValidationInfo;

public class AgeValidator implements Validator<Contract>{

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
