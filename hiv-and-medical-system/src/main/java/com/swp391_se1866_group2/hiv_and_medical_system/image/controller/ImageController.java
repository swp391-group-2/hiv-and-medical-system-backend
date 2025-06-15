package com.swp391_se1866_group2.hiv_and_medical_system.image.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.image.dto.response.ImageResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.image.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Image", description = "Quản lí các hình ảnh bác sĩ upload")
public class ImageController {
    ImageService imageService;

    @PostMapping("/doctors/{doctorId}/upload")
    public ApiResponse<ImageResponse> uploadDoctor(@RequestParam MultipartFile file, @PathVariable("doctorId") String doctorId) {
        return ApiResponse.<ImageResponse>builder()
                .success(true)
                .data(imageService.saveImage(file, doctorId))
                .build();
    }

//    @GetMapping("/doctors/{doctorId}/image")
//    public ApiResponse<ImageResponse> getDoctorImage(@PathVariable("doctorId") String doctorId) {
//
//    }
}
