package com.swp391_se1866_group2.hiv_and_medical_system.payment.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Optional<Payment> findById(int id);

    Optional<Payment> findBySessionId(String sessionId);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.status = 'PAID'")
    long getTotalRevenue();

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.status = 'PAID' AND DATE(p.succeededAt) BETWEEN :startDate AND :endDate")
    long getRevenue(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
