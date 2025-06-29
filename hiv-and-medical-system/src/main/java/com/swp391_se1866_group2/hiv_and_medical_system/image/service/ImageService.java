package com.swp391_se1866_group2.hiv_and_medical_system.image.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.swp391_se1866_group2.hiv_and_medical_system.blogpost.entity.BlogPost;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.ImageMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.repository.DoctorRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.service.DoctorService;
import com.swp391_se1866_group2.hiv_and_medical_system.image.dto.response.ImageResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.image.entity.Image;
import com.swp391_se1866_group2.hiv_and_medical_system.image.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImageService {
    ImageRepository imageRepository;
    DoctorService doctorService;
    Cloudinary cloudinary;

    ImageMapper imageMapper;
    private final DoctorRepository doctorRepository;

    public ImageResponse saveImage(MultipartFile file, String doctorId) {
        try {
            if(file.isEmpty()){
                throw new AppException(ErrorCode.IMAGE_WRONG_TYPE);
            }
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "avatar"));
            Doctor doctor = doctorService.getDoctorById(doctorId);
            String imageUrl = uploadResult.get("secure_url").toString();
            Image image = new Image();
            image.setUrl(imageUrl);
            image.setActive(true);
            if(doctor.getImage() != null){
                doctor.getImage().forEach(img -> img.setActive(false));
            }
            image.setDoctor(doctor);
            doctor.getImage().add(image);
            doctorRepository.save(doctor);
            return imageMapper.toImageResponse(image);
        } catch (IOException exception){
            log.error(exception.getMessage());
            throw new AppException(ErrorCode.UPLOAD_FAILED);
        }
    }


    public BlogPost saveBlogPostImage(MultipartFile file, BlogPost blogPost) {
        try {
            if(file.isEmpty()){
                throw new AppException(ErrorCode.IMAGE_WRONG_TYPE);
            }
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "avatar"));
            String imageUrl = uploadResult.get("secure_url").toString();
            Image image = new Image();
            image.setUrl(imageUrl);
            image.setActive(true);
            if(blogPost != null && blogPost.getImage() != null){
                blogPost.getImage().forEach(img -> img.setActive(false));
                image.setBlogPost(blogPost);
                blogPost.getImage().add(image);
            }else{
                image.setBlogPost(blogPost);
                List<Image> images = new ArrayList<>();
                blogPost.setImage(images);
                blogPost.getImage().add(image);
            }
            return blogPost;
        } catch (IOException exception){
            log.error(exception.getMessage());
            throw new AppException(ErrorCode.UPLOAD_FAILED);
        }
    }

}
