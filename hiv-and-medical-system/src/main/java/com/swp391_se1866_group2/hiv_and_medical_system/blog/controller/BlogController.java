package com.swp391_se1866_group2.hiv_and_medical_system.blog.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.blog.dto.request.BlogCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.blog.dto.request.BlogUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.blog.dto.response.BlogResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.blog.service.BlogService;
import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Blog API", description = "Quản lý blog")
public class BlogController {
    BlogService blogService;

    @PostMapping
    public ApiResponse<BlogResponse> createBlog (@RequestBody @Valid BlogCreationRequest request){
        return ApiResponse.<BlogResponse>builder()
                .success(true)
                .data(blogService.createBlog(request))
                .build();
    }

    @GetMapping
    public ApiResponse<Slice<BlogResponse>> getAllBlogs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        return ApiResponse.<Slice<BlogResponse>>builder()
                .success(true)
                .data(blogService.getAllBlogs(page,size))
                .build();
    }

    @GetMapping("/{blogId}")
    public ApiResponse<BlogResponse> getBlogById(@PathVariable int blogId) {
        return ApiResponse.<BlogResponse>builder()
                .data(blogService.getBlogById(blogId))
                .success(true)
                .build();
    }

    @GetMapping("/title/{title}")
    public ApiResponse<BlogResponse> getBlogByTitle(@PathVariable String title) {
        return ApiResponse.<BlogResponse>builder()
                .data(blogService.getBlogByTitle(title))
                .success(true)
                .build();
    }

    @PutMapping("/{blogId}")
    public ApiResponse<BlogResponse> updateMedication (@PathVariable int blogId, @RequestBody BlogUpdateRequest request){
        return ApiResponse.<BlogResponse>builder()
                .data(blogService.updateBlog(blogId,request))
                .success(true)
                .build();
    }
}

