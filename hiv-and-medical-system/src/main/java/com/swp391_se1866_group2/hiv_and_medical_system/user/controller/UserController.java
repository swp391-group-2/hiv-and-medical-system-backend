package com.swp391_se1866_group2.hiv_and_medical_system.user.controller;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

}
