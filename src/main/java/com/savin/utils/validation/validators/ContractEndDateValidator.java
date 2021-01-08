package com.savin.utils.validation.validators;

import com.savin.contracts.Contract;
import com.savin.enums.ValidationStatus;
import com.savin.utils.validation.ValidationInfo;

import java.time.LocalDate;

/**
 * This class is used to validate contract by its end date
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class ContractEndDateValidator implements Validator<Contract> {

    /**
     * A minimal date when the contract can end
     */
    private LocalDate minEndDate;

    /**
     * @return contract minimal end date
     */
    public LocalDate getMinEndDate() {
        return minEndDate;
    }

    /**
     * @param minEndDate contract minimal end date to set (a date without a time-zone in the ISO-8601 calendar system)
     */
    public void setMinEndDate(LocalDate minEndDate) {
        this.minEndDate = minEndDate;
    }

    /**
     * Creates a new validator to validate contract end date
     */
    public ContractEndDateValidator() {
        this.minEndDate = LocalDate.of(2020, 11, 13);
    }

    /**
     * Creates a new validator to validate contract end date
     *
     * @param minEndDate date border
     */
    public ContractEndDateValidator(LocalDate minEndDate) {
        this.minEndDate = minEndDate;
    }

    /**
     * This method validates given contract by end date. Contract's end date can't be before minimal end date
     *
     * @param contract person's(contract holder's) contract. So we can get information about the contract end date
     * @return result of the validation
     */
    @Override
    public ValidationInfo validate(Contract contract) {
        if (contract.getEndDate().isBefore(minEndDate)) {
            return new ValidationInfo(ValidationStatus.ERROR,
                    "Contract is not valid. The contract expired");
        }
        else {
            return new ValidationInfo(ValidationStatus.FINE,
                    "Contract is valid");
        }
    }
}
