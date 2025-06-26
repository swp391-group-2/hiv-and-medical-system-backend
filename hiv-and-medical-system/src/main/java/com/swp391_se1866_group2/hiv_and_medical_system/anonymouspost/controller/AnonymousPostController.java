package com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.dto.request.AnonymousPostCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.dto.response.AnonymousPostResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.service.AnonymousPostService;
import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anonymous-posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnonymousPostController {
    AnonymousPostService anonymousPostService;

    @PostMapping
    public ApiResponse<AnonymousPostResponse> createAnonymousPost(@RequestBody @Valid AnonymousPostCreationRequest request){
        return ApiResponse.<AnonymousPostResponse>builder()
                .success(true)
                .data(anonymousPostService.createAnonymousPost(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<AnonymousPostResponse>> getAllAnonymousPosts() {
        return ApiResponse.<List<AnonymousPostResponse>>builder()
                .success(true)
                .data(anonymousPostService.getAllAnonymousPosts())
                .build();
    }

}
