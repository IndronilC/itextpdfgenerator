package com.kanini.pdfgenerator.itextpdfgenerator.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name="customers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="customer_surrogate_id", nullable = false)
    private UUID customerSurrogateId;
    @Column(name="customer_id", nullable = false)
    private UUID customerId;
    @Column(name="customer_name", nullable = false)
    private String customerName;
    @Column(name="date_of_birth", nullable = false)
    private LocalDate dob;
    @Column(name="gross_salary", nullable = false)
    private BigDecimal grossSalary;
    @Column(name="net_salary", nullable = false)
    private BigDecimal netSalary;
    @Column(name="created_At", nullable = false)
    private OffsetDateTime createdAt;
    @Column(name="updated_At", nullable = false)
    private OffsetDateTime updatedAt;

}
