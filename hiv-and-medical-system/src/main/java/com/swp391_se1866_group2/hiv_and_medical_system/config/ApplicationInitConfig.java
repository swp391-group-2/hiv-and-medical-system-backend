package com.swp391_se1866_group2.hiv_and_medical_system.config;

import com.swp391_se1866_group2.hiv_and_medical_system.security.service.RoleService;
import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import com.swp391_se1866_group2.hiv_and_medical_system.user.repository.UserRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    RoleService roleService;
    UserRepository userRepository;

    @Bean
    ApplicationRunner applicationRunner(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        return  args -> {
            if(userRepository.findByPhoneNumber("admin").isEmpty()){
                User user = User
                        .builder()
                        .phoneNumber("admin")
                        .password(passwordEncoder.encode("admin"))
                        .role(roleService.getOrCreateRole("ADMIN"))
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
