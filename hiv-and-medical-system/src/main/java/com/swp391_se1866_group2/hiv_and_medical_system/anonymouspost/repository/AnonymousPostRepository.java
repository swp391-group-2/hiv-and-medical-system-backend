package com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.entity.AnonymousPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnonymousPostRepository extends JpaRepository<AnonymousPost, Integer> {

    @Query(value = "SELECT * FROM anonymous_post a WHERE LOWER(a.title) COLLATE utf8mb4_unicode_ci LIKE LOWER(:title) ", nativeQuery = true)
    Page<AnonymousPost> getAll (Pageable pageable, @Param("title") String title);

}
