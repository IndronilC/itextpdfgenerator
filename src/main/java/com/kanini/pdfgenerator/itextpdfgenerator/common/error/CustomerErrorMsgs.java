package com.kanini.pdfgenerator.itextpdfgenerator.common.error;

public enum CustomerErrorMsgs {

    CUSTOMER_ERROR_MSGS_NO_CUST_RESOURCE_FOUND("Please provide correct Id. " +
            "The Customer Resource is not present ");

    private String errorValue;

    CustomerErrorMsgs(String errorValue) {
        this.errorValue = errorValue;
    }

    public String getErrorValue() {
        return this.errorValue;
    }
}
