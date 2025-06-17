package com.swp391_se1866_group2.hiv_and_medical_system.service.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ServiceType;
import com.swp391_se1866_group2.hiv_and_medical_system.service.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Integer> {
    boolean existsByServiceType(ServiceType serviceType);
    boolean existsById(int id);

    Optional<ServiceEntity> findByServiceType(ServiceType serviceType);
}
