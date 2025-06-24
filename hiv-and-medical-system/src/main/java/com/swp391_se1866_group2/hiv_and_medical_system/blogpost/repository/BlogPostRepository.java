package com.swp391_se1866_group2.hiv_and_medical_system.blogpost.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.blogpost.dto.response.BlogPostResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.blogpost.entity.BlogPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {
    boolean existsByTitle(String title);

    @Query("SELECT new com.swp391_se1866_group2.hiv_and_medical_system.blogpost.dto.response.BlogPostResponse(b.id, b.title, b.author, b.snippet, b.content) FROM BlogPost b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Optional<BlogPostResponse> findByTitle(@Param("title") String title);

    @Query("""
        SELECT new com.swp391_se1866_group2.hiv_and_medical_system.blogpost.dto.response.BlogPostResponse(
            b.id, b.author, b.title, b.snippet, b.content) FROM BlogPost b""")
    Optional<Slice<BlogPostResponse>> getAllBlogPosts(Pageable pageable);
}
