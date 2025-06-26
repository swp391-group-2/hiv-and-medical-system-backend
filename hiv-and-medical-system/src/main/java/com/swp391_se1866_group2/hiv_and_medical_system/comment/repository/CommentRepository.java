package com.swp391_se1866_group2.hiv_and_medical_system.comment.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Integer> {
}
