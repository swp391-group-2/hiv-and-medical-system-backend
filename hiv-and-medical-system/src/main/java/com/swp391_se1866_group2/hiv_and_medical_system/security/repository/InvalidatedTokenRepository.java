package com.swp391_se1866_group2.hiv_and_medical_system.security.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.security.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository  extends JpaRepository <InvalidatedToken, String>  {
}
