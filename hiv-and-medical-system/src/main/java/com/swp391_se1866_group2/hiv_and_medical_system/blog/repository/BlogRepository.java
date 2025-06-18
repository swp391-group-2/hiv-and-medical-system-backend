package com.swp391_se1866_group2.hiv_and_medical_system.blog.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.blog.dto.response.BlogResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.blog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
    boolean existsByTitle(String title);
    Optional<BlogResponse>findByTitleIgnoreCase(String title);
    Slice<BlogResponse>getAllBlogs(Pageable pageable);
}
