package com.nazar.uniyat.intelliarts_test_project.exceptions;

public class WrongInputException extends BaseException {

    private static final String EXCEPTION_MESSAGE = "wrong input! ";

    public WrongInputException(String message) {
        super(EXCEPTION_MESSAGE + message);
    }
}
