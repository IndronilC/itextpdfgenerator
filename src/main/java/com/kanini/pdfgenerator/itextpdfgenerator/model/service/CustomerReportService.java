package com.kanini.pdfgenerator.itextpdfgenerator.model.service;

import com.kanini.pdfgenerator.itextpdfgenerator.dto.CustomerRequest;
import com.kanini.pdfgenerator.itextpdfgenerator.dto.CustomerResponse;
import org.springframework.core.io.InputStreamResource;

import java.util.List;
import java.util.UUID;

public interface CustomerReportService {

    public InputStreamResource generateCustomerReport(CustomerRequest customerRequest);
    public List<CustomerResponse> getCustomerDetails();
    public CustomerResponse createCustomer(CustomerRequest customerRequest);
    public CustomerResponse getCustomerById(UUID customerId);


   public CustomerResponse updateCustomer(UUID customerId, CustomerRequest customerRequest);
}
