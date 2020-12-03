package com.savin.utils.validation.validators;

import com.savin.utils.validation.ValidationInfo;

public interface Validator<E> {
    ValidationInfo validate(E validationObject);
}
