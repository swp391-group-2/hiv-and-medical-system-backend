package com.swp391_se1866_group2.hiv_and_medical_system.security.service;

import com.swp391_se1866_group2.hiv_and_medical_system.security.entity.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.security.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role findRoleById(String roleName) {
        return roleRepository.findById(roleName).orElseThrow(null);
    }

    public Role getOrCreateRole(String roleName) {
        return roleRepository.findById(roleName)
                .orElseGet(() -> roleRepository.save(Role.builder().name(roleName).build()));
    }

}
