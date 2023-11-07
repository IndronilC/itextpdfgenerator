package com.kanini.pdfgenerator.itextpdfgenerator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("customerName")
    private String customerName;
    @JsonProperty("dateOfBirth")
    private String dob;
    @JsonProperty("grossSalary")
    private BigDecimal grossSalary;
    @JsonProperty("netSalary")
    private BigDecimal netSalary;
}
