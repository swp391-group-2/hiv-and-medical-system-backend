package com.swp391_se1866_group2.hiv_and_medical_system.blogpost.service;


import com.swp391_se1866_group2.hiv_and_medical_system.blogpost.dto.request.BlogPostCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.blogpost.dto.request.BlogPostUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.blogpost.dto.response.BlogPostResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.blogpost.entity.BlogPost;
import com.swp391_se1866_group2.hiv_and_medical_system.blogpost.repository.BlogPostRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.BlogPostMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.image.entity.Image;
import com.swp391_se1866_group2.hiv_and_medical_system.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class BlogPostService {
    BlogPostMapper blogPostMapper;
    BlogPostRepository blogPostRepository;
    ImageService imageService;

    public BlogPostResponse createBlog(BlogPostCreationRequest request, MultipartFile image) {
        if(blogPostRepository.existsByTitle(request.getTitle())){
            throw new AppException(ErrorCode.BLOG_POST_EXISTED);
        }

        BlogPost blogPost = blogPostMapper.toBlogPost(request);
        if(image != null){
            blogPost = imageService.saveBlogPostImage(image ,blogPost);
        }
        return blogPostMapper.toBlogPostResponse(blogPostRepository.save(blogPost));
    }

    public List<BlogPostResponse> getAllBlogs(Pageable pageable){
        Slice<BlogPostResponse> slicedBlog = blogPostRepository.getAllBlogPosts(pageable).orElseThrow(() -> new AppException(ErrorCode.BLOG_POST_NOT_EXISTED));
        return slicedBlog.getContent();
    }

    public List<BlogPostResponse> searchBlogByTitle(String title, Pageable pageable) {
        Slice<BlogPostResponse> slice = blogPostRepository.searchByTitle(title, pageable).orElseThrow(() -> new AppException(ErrorCode.BLOG_POST_NOT_EXISTED));
        return slice.getContent();
    }

    public BlogPostResponse getBlogById(int blogId) {
        BlogPost blogPost = blogPostRepository.findById(blogId)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_POST_NOT_EXISTED));
        return blogPostMapper.toBlogPostResponse(blogPost);
    }

    public BlogPostResponse updateBlog(int blogId, BlogPostUpdateRequest request, MultipartFile image) {
        BlogPost blogPost = blogPostRepository.findById(blogId)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_POST_NOT_EXISTED));

        blogPostMapper.updateBlogPost(blogPost, request);
        if(image != null && !image.isEmpty()){
            blogPost = imageService.saveBlogPostImage(image,blogPost);
        }
        return blogPostMapper.toBlogPostResponse(blogPostRepository.save(blogPost));
    }
}
