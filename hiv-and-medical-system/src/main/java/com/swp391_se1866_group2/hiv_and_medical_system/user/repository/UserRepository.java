package com.swp391_se1866_group2.hiv_and_medical_system.user.repository;

import java.util.List;
import java.util.Optional;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findByRole(String role);


}
