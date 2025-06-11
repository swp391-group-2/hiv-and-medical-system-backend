package com.swp391_se1866_group2.hiv_and_medical_system.service.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.service.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

}
