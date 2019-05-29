package com.nazar.uniyat.intelliarts_test_project.exceptions;

public class ConnectionExternalApiException extends BaseException {

    private static final String EXCEPTION_MESSAGE = "oops, something went wrong. ";

    public ConnectionExternalApiException(String message) {
        super(EXCEPTION_MESSAGE + message);
    }
}
