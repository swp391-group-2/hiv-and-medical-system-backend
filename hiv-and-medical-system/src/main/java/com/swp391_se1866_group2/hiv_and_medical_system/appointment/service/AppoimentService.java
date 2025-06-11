package com.swp391_se1866_group2.hiv_and_medical_system.appointment.service;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.repository.AppoimentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppoimentService {
    AppoimentRepository appoimentRepository;
}
