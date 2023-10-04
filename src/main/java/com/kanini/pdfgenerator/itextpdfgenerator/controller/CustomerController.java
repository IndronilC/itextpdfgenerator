package com.kanini.pdfgenerator.itextpdfgenerator.controller;

import com.kanini.pdfgenerator.itextpdfgenerator.dto.CustomerRequest;
import com.kanini.pdfgenerator.itextpdfgenerator.model.service.CustomerReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.InputStreamResource;

@Slf4j
@RestController
@RequestMapping("api/pdf")
public class CustomerController {
    @Autowired
    CustomerReportService customerReportService;

    @GetMapping("/customers")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void generateCustomerReport(@RequestBody CustomerRequest customerRequest) {
     InputStreamResource resource = customerReportService.generateCustomerReport(customerRequest);
    }

}
