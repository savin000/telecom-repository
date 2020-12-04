package com.savin.enums;

/**
 * Status of validation
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public enum ValidationStatus {

    /**
     * No critical validation errors occurred, but something is not ok.
     * The object can pass validation or fail. It depends on the developer's decision
     */
    WARNING,

    /**
     * Something went wrong. Object failed validation
     */
    ERROR,

    /**
     * Everything is ok. Object passed validation
     */
    FINE
}
