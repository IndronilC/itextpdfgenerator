package com.kanini.pdfgenerator.itextpdfgenerator.model.service.impl;

import com.kanini.pdfgenerator.itextpdfgenerator.dto.CustomerRequest;
import com.kanini.pdfgenerator.itextpdfgenerator.dto.CustomerResponse;
import com.kanini.pdfgenerator.itextpdfgenerator.model.entities.CustomerEntity;
import com.kanini.pdfgenerator.itextpdfgenerator.model.repositories.CustomerRepository;
import com.kanini.pdfgenerator.itextpdfgenerator.model.service.CustomerReportService;
import com.kanini.pdfgenerator.itextpdfgenerator.model.service.exception.CustomerBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerReportServiceImpl implements CustomerReportService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ModelMapper modelMapper;

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

    @Override
    public List<CustomerResponse> getCustomerDetails() {
        try {
            log.info("In {} method ", CustomerReportServiceImpl.class.getMethod("getCustomerDetails", null));
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new CustomerBusinessException(noSuchMethodException.getMessage(), noSuchMethodException);
        }
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        List<CustomerResponse> customerResponseList = customerEntities.stream().map(customerEntity ->
                convertCustomerEntityToCustomerResponse(customerEntity)).collect(Collectors.toList());
        log.info("Customer Details Retrieval Has Been Executed Successfully");
        return customerResponseList;
    }

    public CustomerResponse convertCustomerEntityToCustomerResponse(CustomerEntity customerEntity) {
        CustomerResponse customerResponse = this.modelMapper.map(customerEntity, CustomerResponse.class);
        return customerResponse;
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
