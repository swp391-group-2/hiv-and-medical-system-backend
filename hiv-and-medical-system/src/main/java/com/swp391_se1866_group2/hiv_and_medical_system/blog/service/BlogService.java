//package com.swp391_se1866_group2.hiv_and_medical_system.blog.service;
//
//import com.swp391_se1866_group2.hiv_and_medical_system.blog.dto.request.BlogCreationRequest;
//import com.swp391_se1866_group2.hiv_and_medical_system.blog.dto.request.BlogUpdateRequest;
//import com.swp391_se1866_group2.hiv_and_medical_system.blog.dto.response.BlogResponse;
//import com.swp391_se1866_group2.hiv_and_medical_system.blog.entity.Blog;
//import com.swp391_se1866_group2.hiv_and_medical_system.blog.repository.BlogRepository;
//import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
//import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
//import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.BlogMapper;
//import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.request.MedicationUpdateRequest;
//import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.response.MedicationResponse;
//import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Slice;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
//@Transactional
//public class BlogService {
//    BlogMapper blogMapper;
//    BlogRepository blogRepository;
//
//    public BlogResponse createBlog(BlogCreationRequest request){
//        if(blogRepository.existsByTitle(request.getTitle())){
//            throw new AppException(ErrorCode.BLOG_EXISTED);
//        }
//
//        Blog blog = blogMapper.toBlog(request);
//        return blogMapper.toBlogResponse(blogRepository.save(blog));
//    }
//
//    public Slice<BlogResponse> getAllBlogs(int page, int size){
//        Pageable pageable = PageRequest.of(page, size);
//        return blogRepository.getAllBlogs(pageable);
//
//    }
//
//    public BlogResponse getBlogByTitle(String title) {
//        String editTitle = title.trim();
//        return blogRepository.findByTitleIgnoreCase(editTitle)
//                .orElseThrow(()-> new AppException(ErrorCode.BLOG_NOT_EXISTED));
//    }
//
//    public BlogResponse getBlogById(int blogId) {
//        Blog blog = blogRepository.findById(blogId)
//                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXISTED));
//        return blogMapper.toBlogResponse(blog);
//    }
//
//    public BlogResponse updateBlog(int blogId, BlogUpdateRequest request) {
//        Blog blog = blogRepository.findById(blogId)
//                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXISTED));
//
//        blogMapper.updateBlog(blog,request);
//        return blogMapper.toBlogResponse(blogRepository.save(blog));
//    }
//
//
//
//}
