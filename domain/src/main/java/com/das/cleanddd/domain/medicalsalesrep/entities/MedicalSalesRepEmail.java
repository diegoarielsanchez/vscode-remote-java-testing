package com.das.cleanddd.domain.medicalsalesrep.entities;

import com.das.cleanddd.domain.shared.StringValueObject;

public class MedicalSalesRepEmail  extends StringValueObject {
    // OWASP Validation Regular Expression
    // https://owasp.org/www-community/OWASP_Validation_Regex_Repository
                                      //^[a-zA-Z0-9_+&*-] + (?:\\.[a-zA-Z0-9_+&*-] + )*@(?:[a-zA-Z0-9-]+\\.) + [a-zA-Z]{2, 7}
    // Regular Expression to Restrict Consecutive, Trailing, and Leading Dots
    // https://www.baeldung.com/java-email-validation-regex#regular-expression-to-restrict-consecutive-trailing-and-leading-dots
                                      //^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$
    public static final String REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    public static final String ERROR_MESSAGE_NULL = "Email cannot be null.";
    public static final String ERROR_MESSAGE_EMPTY = "Email cannot be empty.";
    public static final String ERROR_MESSAGE_INVALID = "Email is not valid.";
    public static final String ERROR_MESSAGE_INVALID_FORMAT = "Email format is not valid.";
    public static final String ERROR_MESSAGE_INVALID_LENGTH = "Email length is not valid.";
    public static final int MAX_LENGTH = 100;
    public static final int MIN_LENGTH = 5;
    public static final String ERROR_MESSAGE = "Email must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long and contain only letters, numbers, and special characters.";
    public static final String ERROR_MESSAGE_INVALID_CHARACTERS = "Email contains invalid characters.";

    public  MedicalSalesRepEmail(String value) {
        super(value);
    // }
    // public MedicalSalesRepEmail(String value, String regex) {
    //        super(value);
        if (value == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NULL);
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_EMPTY);
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_LENGTH);
        }
        if (!value.matches(REGEX)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_FORMAT);
        }
     }

    public MedicalSalesRepEmail() {
        super("");
    }
}
