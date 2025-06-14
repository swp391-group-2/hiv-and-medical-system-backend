package com.swp391_se1866_group2.hiv_and_medical_system.image.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
}
