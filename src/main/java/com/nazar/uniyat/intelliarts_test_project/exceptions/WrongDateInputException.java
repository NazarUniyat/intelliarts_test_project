package com.nazar.uniyat.intelliarts_test_project.exceptions;

public class WrongDateInputException extends BaseException {

    private static final String EXCEPTION_MESSAGE = "you try to input date in wrong format, please use YYYY-MM-DD";

    public WrongDateInputException() {
        super(EXCEPTION_MESSAGE);
    }
}
