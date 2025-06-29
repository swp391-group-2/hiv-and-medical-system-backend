package com.swp391_se1866_group2.hiv_and_medical_system.blogpost.controller;


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

    @PostMapping
    @Operation(summary = "Thêm blog mới")
    public ApiResponse<BlogPostResponse> createBlog(@RequestBody @Valid BlogPostCreationRequest request, @RequestParam MultipartFile file){
        return ApiResponse.<BlogPostResponse>builder()
                .success(true)
                .data(blogPostService.createBlog(request, file))
                .build();
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách blog theo phân trang")
    public ApiResponse<List<BlogPostResponse>> getAllBlogs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("b.title"));
        return ApiResponse.<List<BlogPostResponse>>builder()
                .success(true)
                .data(blogPostService.getAllBlogs(pageable))
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

    @GetMapping("/title/{title}")
    @Operation(summary = "Lấy blog theo title")
    public ApiResponse<BlogPostResponse> getBlogByTitle(@PathVariable String title) {
        return ApiResponse.<BlogPostResponse>builder()
                .data(blogPostService.getBlogByTitle(title))
                .success(true)
                .build();
    }

    @PutMapping("/{blogId}")
    @Operation(summary = "Cập nhật blog")
    public ApiResponse<BlogPostResponse> updateBlog(@PathVariable int blogId, @RequestBody BlogPostUpdateRequest request){
        return ApiResponse.<BlogPostResponse>builder()
                .data(blogPostService.updateBlog(blogId,request))
                .success(true)
                .build();
    }
}
