package com.kanini.pdfgenerator.itextpdfgenerator.controller;

import com.kanini.pdfgenerator.itextpdfgenerator.dto.CustomerRequest;
import com.kanini.pdfgenerator.itextpdfgenerator.dto.CustomerResponse;
import com.kanini.pdfgenerator.itextpdfgenerator.model.service.CustomerReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.InputStreamResource;

import java.util.List;
import java.util.UUID;

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
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody CustomerRequest customerRequest) {
        log.debug("Customer Request Details {} ", customerRequest);
        return customerReportService.createCustomer(customerRequest);
    }

    @GetMapping("/customers/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse findByCustomerId(@PathVariable String customerId) {
        log.debug("Customer Id String Details {} ", customerId);
        UUID strToUUCustomerId = getUuid(customerId);
        log.debug("strToUUCustomerId {} ", strToUUCustomerId);
        return customerReportService.getCustomerById(strToUUCustomerId);
    }

    private static UUID getUuid(String customerId) {
        UUID strToUUCustomerId = UUID.fromString(customerId);
        return strToUUCustomerId;
    }

    @PutMapping("/customers/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable String customerId,
            @RequestBody CustomerRequest customerRequest) {
        logDebugDetailsForMethod(customerId, "Customer Id String Details {} in updateCustomer ");
        UUID strToUUCustomerId = getUuid(customerId);
        CustomerResponse customerResponse = customerReportService.updateCustomer(
                strToUUCustomerId, customerRequest);
        return ResponseEntity.ok(customerResponse);
    }

    private static void logDebugDetailsForMethod(String customerId, String message) {
        log.debug(message, customerId);
    }

}
