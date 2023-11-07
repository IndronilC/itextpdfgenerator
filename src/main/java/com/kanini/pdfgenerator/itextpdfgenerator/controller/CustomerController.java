package com.kanini.pdfgenerator.itextpdfgenerator.controller;

import com.kanini.pdfgenerator.itextpdfgenerator.dto.CustomerRequest;
import com.kanini.pdfgenerator.itextpdfgenerator.dto.CustomerResponse;
import com.kanini.pdfgenerator.itextpdfgenerator.model.service.CustomerReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.InputStreamResource;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/pdf")
public class CustomerController {
    @Autowired
    CustomerReportService customerReportService;

    @GetMapping("/customers/report")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void generateCustomerReport(@RequestBody CustomerRequest customerRequest) {
     log.info("In method generateCustomerReport");
     InputStreamResource resource = customerReportService.generateCustomerReport(customerRequest);
    }

    @GetMapping("/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAllDetails() {
        return customerReportService.getCustomerDetails();
    }

    @PostMapping("/customers")
    public CustomerResponse createCustomer(@RequestBody CustomerRequest customerRequest) {
        log.debug("Customer Request Details {} ", customerRequest);
        return customerReportService.createCustomer(customerRequest);
    }

}
