package com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.entity.AnonymousPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnonymousPostRepository extends JpaRepository<AnonymousPost, Integer> {
}
