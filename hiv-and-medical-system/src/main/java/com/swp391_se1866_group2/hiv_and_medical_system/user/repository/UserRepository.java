package com.swp391_se1866_group2.hiv_and_medical_system.user.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findByRole(String role);
    User getUserByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'PATIENT' AND DATE(u.createdAt) BETWEEN :previousStart AND :previousEnd")
    long countPatients(@Param("previousStart") LocalDate previousStart, @Param("previousEnd") LocalDate previousEnd);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'DOCTOR' AND DATE(u.createdAt) BETWEEN :startDate AND :endDate")
    long countDoctors(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'PATIENT' ")
    long countTotalPatients();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'DOCTOR' ")
    long countTotalDoctors();

}
