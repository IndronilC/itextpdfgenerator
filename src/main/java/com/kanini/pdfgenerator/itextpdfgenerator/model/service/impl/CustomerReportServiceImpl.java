package com.kanini.pdfgenerator.itextpdfgenerator.model.service.impl;

import com.kanini.pdfgenerator.itextpdfgenerator.dto.CustomerRequest;
import com.kanini.pdfgenerator.itextpdfgenerator.model.entities.CustomerEntity;
import com.kanini.pdfgenerator.itextpdfgenerator.model.repositories.CustomerRepository;
import com.kanini.pdfgenerator.itextpdfgenerator.model.service.CustomerReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerReportServiceImpl implements CustomerReportService {

    @Autowired
    CustomerRepository customerRepository;
    @Override
    public InputStreamResource generateCustomerReport(CustomerRequest customerRequest) {
        CustomerEntity customerEntity = getCustomerEntity(customerRequest);
        List<CustomerEntity> customerEntities = customerRepository.findByCustomerCriteria(
                customerEntity.getCustomerId(),
                customerEntity.getCustomerName(),
                customerEntity.getDob());
        return null;
    }

    private CustomerEntity getCustomerEntity(CustomerRequest customerRequest) {
        CustomerEntity customerEntity = CustomerEntity.builder()
                .customerId(UUID.fromString(customerRequest.getCustomerId()))
                .customerName(customerRequest.getCustomerName())
                .dob(LocalDate.parse(customerRequest.getDob()))
                .build();
        return customerEntity;
    }
}
