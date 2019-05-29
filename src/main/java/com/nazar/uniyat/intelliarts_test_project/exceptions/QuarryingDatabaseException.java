package com.nazar.uniyat.intelliarts_test_project.exceptions;

public class QuarryingDatabaseException extends BaseException {

    private static final String EXCEPTION_MESSAGE = "some problems with database quarrying ";

    public QuarryingDatabaseException(String message) {
        super(EXCEPTION_MESSAGE + message);
    }
}
