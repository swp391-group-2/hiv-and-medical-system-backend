package com.swp391_se1866_group2.hiv_and_medical_system.payment.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Optional<Payment> findById(int id);

}
