package com.savin.utils.validation;

import com.savin.enums.ValidationStatus;

public class ValidationInfo {
    private ValidationStatus status;
    private String message;

    public ValidationStatus getStatus() {
        return status;
    }

    public void setStatus(ValidationStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ValidationInfo(ValidationStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
