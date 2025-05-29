package com.swp391_se1866_group2.hiv_and_medical_system.security.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
