package com.example.MallManagement.repository;

import com.example.MallManagement.model.Customer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')) AND " +
            "LOWER(c.currency) LIKE LOWER(CONCAT('%', :currency, '%'))")
    List<Customer> findByNameAndCurrency(@Param("name") String name, @Param("currency") String currency, Sort sort);
}