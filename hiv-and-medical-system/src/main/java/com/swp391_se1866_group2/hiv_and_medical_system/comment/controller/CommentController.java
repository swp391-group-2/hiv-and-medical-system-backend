package com.swp391_se1866_group2.hiv_and_medical_system.comment.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.request.CommentCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.response.CommentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.service.CommentService;
import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
    CommentService commentService;

    @PostMapping("/me")
    public ApiResponse<CommentResponse> createComment(@RequestBody @Valid CommentCreationRequest request){
        return ApiResponse.<CommentResponse>builder()
                .success(true)
                .data(commentService.createComment(request))
                .build();
    }

    @GetMapping("/{anonymousPostId}/createdAt")
    public ApiResponse<List<CommentResponse>> getAllCommentsByDate(@PathVariable int anonymousPostId ,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ApiResponse.<List<CommentResponse>>builder()
                .success(true)
                .data(commentService.getAllComments(anonymousPostId,pageable))
                .build();
    }

    @GetMapping("/{anonymousPostId}/content")
    public ApiResponse<List<CommentResponse>> getAllCommentsByContent(@PathVariable int anonymousPostId,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("content"));
        return ApiResponse.<List<CommentResponse>>builder()
                .success(true)
                .data(commentService.getAllComments(anonymousPostId,pageable))
                .build();
    }
}
