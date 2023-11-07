package com.kanini.pdfgenerator.itextpdfgenerator.common.util.datefunction;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;


public class CustomerDateUtil {
    protected CustomerDateUtil(){}

    /**
     * <p> This method has been created to add the current time
     * using {@Code java.time.OffsetDateTime} which is joda time integrated from
     * java 8.</p><br/>
     * <p> This method is called currently by methods of
     * {@link com.kanini.pdfgenerator.itextpdfgenerator.service.impl.CustomerReportServiceImpl}
     * to set values for {@link com.kanini.pdfgenerator.itextpdfgenerator.model.entities.CustomerEntity}
     * for setting their createdAt and updatedAt values which are settled when a Customer is newly
     * onboarded in the Customer Onboarding system, thus added or inserted to the Mysql database
     * </p> <br/>
     * <p> This method has been added to this Utility Class as there was a redundancy of usage of
     * the {@Code java.time.OffsetDateTime.now()} in the code</p>
     * @return -> returns the current system time with date for timestamp columns of the Customer Table
     * in the Customer Onboarding / Customer Schema.
     */
    public static OffsetDateTime getNow() {
        return OffsetDateTime.now();
    }

    public static LocalDate createParsedDate(String strDate, String dateFormatPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatPattern);
        return LocalDate.parse(strDate, formatter);
    }
}
