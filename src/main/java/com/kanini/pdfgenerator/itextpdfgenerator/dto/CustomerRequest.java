package com.kanini.pdfgenerator.itextpdfgenerator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("customerName")
    private String customerName;
    @JsonProperty("dateOfBirth")
    private String dob;


}
