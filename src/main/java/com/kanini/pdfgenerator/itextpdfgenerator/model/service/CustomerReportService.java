package com.kanini.pdfgenerator.itextpdfgenerator.model.service;

import com.kanini.pdfgenerator.itextpdfgenerator.dto.CustomerRequest;
import org.springframework.core.io.InputStreamResource;

public interface CustomerReportService {

    public InputStreamResource generateCustomerReport(CustomerRequest customerRequest);

}
