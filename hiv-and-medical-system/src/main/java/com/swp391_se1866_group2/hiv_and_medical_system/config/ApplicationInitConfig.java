package com.swp391_se1866_group2.hiv_and_medical_system.config;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
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

    UserRepository userRepository;

    @Bean
    ApplicationRunner applicationRunner(UserService userService, PasswordEncoder passwordEncoder) {
        return  args -> {
            if(userRepository.findByEmail("admin@gmail.com").isEmpty()){
                User user = User
                        .builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(Role.ADMIN.name())
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            if(userRepository.findByEmail("manager@gmail.com").isEmpty()){
                User user = User
                        .builder()
                        .email("manager@gmail.com")
                        .password(passwordEncoder.encode("12345678"))
                        .role(Role.MANAGER.name())
                        .build();
                userRepository.save(user);
                log.warn("manager user has been created with default password: manager, please change it");
            }
        };
    }
}
