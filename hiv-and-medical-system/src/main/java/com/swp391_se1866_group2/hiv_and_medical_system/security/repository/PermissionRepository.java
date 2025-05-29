package com.swp391_se1866_group2.hiv_and_medical_system.security.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.security.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
