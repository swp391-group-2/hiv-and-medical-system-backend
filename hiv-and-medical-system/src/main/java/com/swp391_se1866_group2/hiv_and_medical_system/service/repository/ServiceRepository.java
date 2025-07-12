package com.swp391_se1866_group2.hiv_and_medical_system.service.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ServiceType;
import com.swp391_se1866_group2.hiv_and_medical_system.service.dto.response.ServiceResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.service.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Integer> {
    boolean existsByServiceType(ServiceType serviceType);
    boolean existsById(int id);

    Optional<ServiceEntity> findByServiceType(ServiceType serviceType);

    @Query("""
        SELECT new com.swp391_se1866_group2.hiv_and_medical_system.service.dto.response.ServiceResponse(
            s.id, s.name, s.price, s.serviceType, (SELECT i.url FROM Image i WHERE i.serviceEntity.id = s.id AND i.isActive = true)
        ) FROM ServiceEntity s
    """)
    List<ServiceResponse> getAllServices();

    @Query("""
        SELECT new com.swp391_se1866_group2.hiv_and_medical_system.service.dto.response.ServiceResponse(
            s.id, s.name, s.price, s.serviceType, (SELECT i.url FROM Image i WHERE i.serviceEntity.id = s.id AND i.isActive = true)
        ) FROM ServiceEntity s WHERE s.id = :id
    """)
    ServiceResponse getServiceById(int id);

    @Query("""
        SELECT new com.swp391_se1866_group2.hiv_and_medical_system.service.dto.response.ServiceResponse(
            s.id, s.name, s.price, s.serviceType, (SELECT i.url FROM Image i WHERE i.serviceEntity.id = s.id AND i.isActive = true)
        ) FROM ServiceEntity s WHERE s.serviceType = :serviceType
    """)
    ServiceResponse getServiceByServiceType(ServiceType serviceType);

}
