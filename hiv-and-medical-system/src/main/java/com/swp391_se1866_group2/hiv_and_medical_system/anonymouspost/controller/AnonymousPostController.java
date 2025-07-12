package com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.dto.request.AnonymousPostCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.dto.response.AnonymousPostResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.service.AnonymousPostService;
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
    public ApiResponse<List<AnonymousPostResponse>> getAllAnonymousPosts(@RequestParam(name = "title", defaultValue = "") String title, @RequestParam(defaultValue = "desc") String sortOrder, @RequestParam(name = "sortBy", defaultValue = "created_at") String sortBy , @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size, @RequestParam(name = "isPatient", defaultValue = "false") boolean isPatient ) {
        Pageable pageable;
        title = '%' + title.trim() + '%';
        if(sortOrder.equalsIgnoreCase("desc")){
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }else{
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }
        if(isPatient){
            return ApiResponse.<List<AnonymousPostResponse>>builder()
                    .success(true)
                    .data(anonymousPostService.getAllMyAnonymousPosts(title, pageable))
                    .build();
        }
        return ApiResponse.<List<AnonymousPostResponse>>builder()
                .success(true)
                .data(anonymousPostService.getAllAnonymousPosts(pageable, title))
                .build();
    }

    @GetMapping("/my")
    public ApiResponse<List<AnonymousPostResponse>> getAllMyAnonymousPosts(@RequestParam(name = "title", defaultValue = "") String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        title = '%' + title.trim() + '%';
        return ApiResponse.<List<AnonymousPostResponse>>builder()
                .success(true)
                .data(anonymousPostService.getAllMyAnonymousPosts(title, pageable))
                .build();
    }

}
