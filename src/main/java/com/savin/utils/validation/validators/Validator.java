package com.savin.utils.validation.validators;

import com.savin.utils.validation.ValidationInfo;

/**
 * This interface is used to create validators for contracts and other specific entities
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public interface Validator<E> {

    /**
     * This method validates given object
     * @param validationObject object for inspection
     * @return result of the validation
     */
    ValidationInfo validate(E validationObject);
}
