package com.swp391_se1866_group2.hiv_and_medical_system.image.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

}
