package com.swp391_se1866_group2.hiv_and_medical_system.user.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
