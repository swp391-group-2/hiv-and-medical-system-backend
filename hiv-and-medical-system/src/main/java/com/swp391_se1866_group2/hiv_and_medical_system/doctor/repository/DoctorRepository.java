package com.swp391_se1866_group2.hiv_and_medical_system.doctor.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {

    @Query("""
        SELECT new com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse (
            d.id, u.id, u.email, u.fullName, u.status, u.code, d.specialization, d.licenseNumber,
            (SELECT i.url FROM Image i WHERE i.doctor.id = d.id AND i.isActive = true)
        )
        FROM Doctor d JOIN d.user u WHERE u.email = :email""")
    Optional<DoctorResponse> findDoctorByToken(@Param("email") String email);

    @Query(""" 
            SELECT new com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse (d.id, u.id, u.email, u.fullName, u.status, u.code, d.licenseNumber, d.specialization, (SELECT i.url FROM Image i WHERE i.doctor.id = d.id AND i.isActive = true)) FROM Doctor d JOIN d.user u WHERE u.email = :email""")
    Optional<DoctorResponse> findDoctorByUserEmail(@Param("email") String email);

    @Query("""
        SELECT new com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse (
            d.id, u.id, u.email, u.fullName, u.status, u.code, d.specialization, d.licenseNumber, (SELECT i.url FROM Image i WHERE i.doctor.id = d.id AND i.isActive = true)
        )
        FROM Doctor d JOIN d.user u ORDER BY d.id ASC""")
    Optional<List<DoctorResponse>> getTopDoctor (Pageable pageable);


    @Query("""
        SELECT new com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse (
            d.id, u.id, u.email, u.fullName, u.status, u.code, d.specialization, d.licenseNumber, (SELECT i.url FROM Image i WHERE i.doctor.id = d.id AND i.isActive = true)
        )
        FROM Doctor d JOIN d.user u""")
    Optional<Slice<DoctorResponse>> getAllDoctor (Pageable pageable);

    @Query("""
        SELECT new com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse (
            d.id, u.id, u.email, u.fullName, u.status, u.code, d.specialization, d.licenseNumber, (SELECT i.url FROM Image i WHERE i.doctor.id = d.id AND i.isActive = true)
        )
        FROM Doctor d JOIN d.user u WHERE d.id = :id""")
    Optional<DoctorResponse> getDoctorById (@Param("id") String id);


    @Query(""" 
            SELECT new com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse (d.id, u.id, u.email, u.fullName, u.status, u.code, d.licenseNumber, d.specialization, (SELECT i.url FROM Image i WHERE i.doctor.id = d.id AND i.isActive = true)) FROM Doctor d JOIN d.user u WHERE u.email = :email""")
    Optional<Doctor> findDoctorEntityByUserEmail(@Param("email") String email);
}
