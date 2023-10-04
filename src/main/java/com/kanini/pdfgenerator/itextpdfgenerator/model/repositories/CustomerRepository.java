package com.kanini.pdfgenerator.itextpdfgenerator.model.repositories;

import com.kanini.pdfgenerator.itextpdfgenerator.model.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    @Query("select c from CustomerEntity c where c.customerId = :customerId " +
            "and c.customerName = :customerName " +
            "and c.dob = :dob")
    public List<CustomerEntity> findByCustomerCriteria(
            @Param("customerId") UUID customerId,
            @Param("customerName") String customerName,
            @Param("dob") LocalDate dob);

}
