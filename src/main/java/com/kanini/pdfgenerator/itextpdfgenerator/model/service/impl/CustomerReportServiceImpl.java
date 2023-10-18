package com.kanini.pdfgenerator.itextpdfgenerator.model.service.impl;

import com.kanini.pdfgenerator.itextpdfgenerator.dto.CustomerRequest;
import com.kanini.pdfgenerator.itextpdfgenerator.model.entities.CustomerEntity;
import com.kanini.pdfgenerator.itextpdfgenerator.model.repositories.CustomerRepository;
import com.kanini.pdfgenerator.itextpdfgenerator.model.service.CustomerReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@Slf4j
public class CustomerReportServiceImpl implements CustomerReportService {

    @Autowired
    CustomerRepository customerRepository;
    @Override
    public InputStreamResource generateCustomerReport(CustomerRequest customerRequest) {
        CustomerEntity customerEntity = getCustomerEntity(customerRequest);
        log.debug("CustomerEntity data before calling find method - {}", customerEntity);
        List<CustomerEntity> customerEntities = customerRepository.findByCustomerCriteria(
                customerEntity.getCustomerId().toString(),
                customerEntity.getCustomerName(),
                customerEntity.getDob());
        log.debug("Retrieved Customer Details below :");
         customerEntities
                .stream().forEach(getCustomerEntityConsumer());
        return null;
    }

    private  Consumer<CustomerEntity> getCustomerEntityConsumer() {
        return localCustomerEntity -> {
            log.debug(localCustomerEntity.getCustomerName());
            log.debug(localCustomerEntity.getCustomerId().toString());
            log.debug(localCustomerEntity.getCustomerSurrogateId().toString());
            log.debug(localCustomerEntity.getDob().toString());
            log.debug(localCustomerEntity.getGrossSalary().toString());
        };
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
