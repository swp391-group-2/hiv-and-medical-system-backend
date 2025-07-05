package com.swp391_se1866_group2.hiv_and_medical_system.comment.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.blogpost.dto.response.BlogPostResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.response.CommentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Integer> {

    @Query("SELECT new com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.response.CommentResponse(c.id, c.anonymousPost.id, c.doctor.id, c.doctor.user.fullName , c.patient.id, c.content, c.createdAt, (SELECT i.url FROM Image i WHERE i.doctor.id = c.doctor.id AND i.isActive = true)) FROM Comment c WHERE c.anonymousPost.id = :anonymousPostId")
    Optional<Slice<CommentResponse>> getAllComments(@Param("anonymousPostId") int anonymousPostId, Pageable pageable);
}
