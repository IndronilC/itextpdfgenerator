package com.kanini.pdfgenerator.itextpdfgenerator.model.service.impl;

import com.kanini.pdfgenerator.itextpdfgenerator.common.error.CustomerErrorMsgs;
import com.kanini.pdfgenerator.itextpdfgenerator.common.util.datefunction.CustomerDateUtil;
import com.kanini.pdfgenerator.itextpdfgenerator.common.util.uuidgenerator.UUIDGeneratorUtil;
import com.kanini.pdfgenerator.itextpdfgenerator.dto.CustomerRequest;
import com.kanini.pdfgenerator.itextpdfgenerator.dto.CustomerResponse;
import com.kanini.pdfgenerator.itextpdfgenerator.model.entities.CustomerEntity;
import com.kanini.pdfgenerator.itextpdfgenerator.model.repositories.CustomerRepository;
import com.kanini.pdfgenerator.itextpdfgenerator.model.service.CustomerReportService;
import com.kanini.pdfgenerator.itextpdfgenerator.model.service.exception.CustomerBusinessException;
import com.kanini.pdfgenerator.itextpdfgenerator.model.service.exception.cause.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
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

    @Autowired
    @Value("${kanini.dateformat}")
    private String dateFormatPattern;

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

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        try {
            log.info("In {} method ", CustomerReportServiceImpl.class.getMethod("createCustomer", CustomerRequest.class));
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new CustomerBusinessException(noSuchMethodException.getMessage(), noSuchMethodException);
        }
        CustomerEntity customerEntity = getCustomerEntityFromRequest(customerRequest);
        setDateInCustomerEntity(customerRequest, customerEntity);
        customerEntity.setCustomerId(UUIDGeneratorUtil.generateNewUUID());
        customerEntity.setCreatedAt(CustomerDateUtil.getNow());
        customerEntity.setUpdatedAt(CustomerDateUtil.getNow());
        customerEntity = customerRepository.save(customerEntity);
        logSavedCustomerEntityDetails(customerEntity);
        return convertCustomerEntityToCustomerResponse(customerEntity);
    }

    private void setDateInCustomerEntity(CustomerRequest customerRequest, CustomerEntity customerEntity) {
        if (Objects.isNull(customerEntity.getDob())) {
            customerEntity.setDob(getParsedDate(customerRequest));
        }
    }

    private CustomerEntity getCustomerEntityFromRequest(CustomerRequest customerRequest) {
        CustomerEntity customerEntity = convertCustomerRequestToCustomerEntity(customerRequest);
        return customerEntity;
    }

    @Override
    public CustomerResponse getCustomerById(UUID customerId) {
        Optional<CustomerEntity> opCustomerEntity = getCustomerEntityById(customerId);
        // With orElseThrow added the below try catch block will never throw exception
        // but still this will stay as a documentation with the experience that
        // NoSuchElementException can be thrown if optional.get() return null
        // so it will act as an indication or when / if we remove -> orElseThrow
        // lambda expression
        logCustomerData(opCustomerEntity);
        return convertCustomerEntityToCustomerResponse(opCustomerEntity.get());
    }

    private void logCustomerData(Optional<CustomerEntity> opCustomerEntity) {
        try {
            logSavedCustomerEntityDetails(opCustomerEntity.get());
        } catch (Exception e) {
            throw new CustomerBusinessException(
                    CustomerErrorMsgs.CUSTOMER_ERROR_MSGS_NO_CUST_RESOURCE_FOUND.getErrorValue(),
                    new NoSuchElementException());
        }
    }

    private Optional<CustomerEntity> getCustomerEntityById(UUID customerId) {
        Optional<CustomerEntity> opCustomerEntity = Optional.ofNullable(customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerBusinessException(
                        CustomerErrorMsgs.CUSTOMER_ERROR_MSGS_NO_CUST_RESOURCE_FOUND.getErrorValue(),
                        new ResourceNotFoundException())));

        return opCustomerEntity;
    }

    @Override
    public CustomerResponse updateCustomer(
            UUID customerId,
            CustomerRequest customerRequest) {
        Optional<CustomerEntity> optionalCustomerEntity = getCustomerEntityById(customerId);
        logCustomerData(optionalCustomerEntity);
        CustomerEntity customerEntityToUpdate = getCustomerEntityForUpdate(
                optionalCustomerEntity.get(), customerRequest);
        customerEntityToUpdate = customerRepository.save(customerEntityToUpdate);
        logSavedCustomerEntityDetails(customerEntityToUpdate);
        return convertCustomerEntityToCustomerResponse(customerEntityToUpdate);
    }

    private CustomerEntity getCustomerEntityForUpdate(
            CustomerEntity customerEntity,
            CustomerRequest customerRequest) {
        CustomerEntity localCustomerEntity = customerEntity;
        localCustomerEntity.setCustomerName(getCustomerName(customerEntity, customerRequest));
        localCustomerEntity.setDob(getDob(customerEntity, customerRequest));
        localCustomerEntity.setGrossSalary(getGrossSalary(customerEntity, customerRequest));
        localCustomerEntity.setNetSalary(getNetSalary(customerEntity, customerRequest));
        localCustomerEntity.setUpdatedAt(CustomerDateUtil.getNow());
        return localCustomerEntity;
    }

    private static BigDecimal getNetSalary(CustomerEntity customerEntity, CustomerRequest customerRequest) {
        return Objects.nonNull(customerRequest.getNetSalary()) ?
                customerRequest.getNetSalary() : customerEntity.getNetSalary();
    }

    private static BigDecimal getGrossSalary(CustomerEntity customerEntity, CustomerRequest customerRequest) {
        return Objects.nonNull(customerRequest.getGrossSalary()) ?
                customerRequest.getGrossSalary() : customerEntity.getGrossSalary();
    }

    private LocalDate getDob(CustomerEntity customerEntity, CustomerRequest customerRequest) {
        return Objects.nonNull(customerRequest.getDob())
                ? getParsedDate(customerRequest) : customerEntity.getDob();
    }

    private String getCustomerName(CustomerEntity customerEntity, CustomerRequest customerRequest) {
        return Objects.nonNull(customerRequest.getCustomerName())
                ? customerRequest.getCustomerName() : customerEntity.getCustomerName();
    }

    private LocalDate getParsedDate(CustomerRequest customerRequest) {
        String localDateFormatPattern = dateFormatPattern;
        return CustomerDateUtil.createParsedDate(customerRequest.getDob(), localDateFormatPattern);
    }

    private void logSavedCustomerEntityDetails(CustomerEntity customerEntity) {
        log.debug(customerEntity.getCustomerName());
        log.debug(customerEntity.getCustomerId().toString());
        log.debug(customerEntity.getCustomerSurrogateId().toString());
        log.debug(customerEntity.getDob().toString());
        log.debug(customerEntity.getNetSalary().toString());
        log.debug(customerEntity.getGrossSalary().toString());
    }

    private CustomerEntity convertCustomerRequestToCustomerEntity(CustomerRequest customerRequest) {
        CustomerEntity customerEntity = this.modelMapper.map(customerRequest, CustomerEntity.class);
        return customerEntity;
    }

    public CustomerResponse convertCustomerEntityToCustomerResponse(CustomerEntity customerEntity) {
        CustomerResponse customerResponse = this.modelMapper.map(customerEntity, CustomerResponse.class);
        return customerResponse;
    }

    private Consumer<CustomerEntity> getCustomerEntityConsumer() {
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
                .dob(getParsedDate(customerRequest))
                .build();

        return customerEntity;
    }

}
