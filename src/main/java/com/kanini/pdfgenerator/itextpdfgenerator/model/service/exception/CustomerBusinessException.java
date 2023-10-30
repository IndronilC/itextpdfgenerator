package com.kanini.pdfgenerator.itextpdfgenerator.model.service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerBusinessException extends RuntimeException {
    private String message;
    private Throwable cause;
}
