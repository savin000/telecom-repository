package com.savin.utils.validation;

import com.savin.enums.ValidationStatus;

/**
 * This class represents information about validation of an object
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class ValidationInfo {

    /**
     * Status of validation
     * @see ValidationStatus
     */
    private ValidationStatus status;

    /**
     * A message that contains additional information about validation
     */
    private String message;

    /**
     * @return validation status
     */
    public ValidationStatus getStatus() {
        return status;
    }

    /**
     * @param status status to set
     */
    public void setStatus(ValidationStatus status) {
        this.status = status;
    }

    /**
     * @return validation message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Creates a new ValidationInfo object with the given status and message
     *
     * @param status validation status to set
     * @param message validation message to set
     */
    public ValidationInfo(ValidationStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
