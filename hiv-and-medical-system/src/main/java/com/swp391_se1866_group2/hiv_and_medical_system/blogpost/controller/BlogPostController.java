package com.swp391_se1866_group2.hiv_and_medical_system.blogpost.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.blogpost.dto.request.BlogPostCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.blogpost.dto.request.BlogPostUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.blogpost.dto.response.BlogPostResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.blogpost.service.BlogPostService;
import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Blog API", description = "Quản lý blog")
public class BlogPostController {
    BlogPostService blogPostService;
    ObjectMapper objectMapper;

    @PostMapping
    @Operation(summary = "Thêm blog mới")
    public ApiResponse<BlogPostResponse> createBlog(@RequestParam("data") String jsonData, @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        BlogPostCreationRequest request = objectMapper.readValue(jsonData, BlogPostCreationRequest.class);
        return ApiResponse.<BlogPostResponse>builder()
                .success(true)
                .data(blogPostService.createBlog(request, file))
                .build();
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách blog theo phân trang")
    public ApiResponse<List<BlogPostResponse>> getAllBlogs(@RequestParam(required = false, defaultValue = "") String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("b.title"));
        return ApiResponse.<List<BlogPostResponse>>builder()
                .success(true)
                .data(blogPostService.getAllBlogs(pageable, title))
                .build();
    }

    @GetMapping("/{blogId}")
    @Operation(summary = "Lấy blog theo id")
    public ApiResponse<BlogPostResponse> getBlogById(@PathVariable int blogId) {
        return ApiResponse.<BlogPostResponse>builder()
                .data(blogPostService.getBlogById(blogId))
                .success(true)
                .build();
    }

    @PutMapping("/{blogId}")
    @Operation(summary = "Cập nhật blog")
    public ApiResponse<BlogPostResponse> updateBlog(@PathVariable int blogId, @RequestParam("data") String jsonData, @RequestParam(value = "file",  required = false)  MultipartFile file) throws JsonProcessingException {
        BlogPostUpdateRequest request = objectMapper.readValue(jsonData, BlogPostUpdateRequest.class);
        return ApiResponse.<BlogPostResponse>builder()
                .data(blogPostService.updateBlog(blogId,request, file))
                .success(true)
                .build();
    }



}
