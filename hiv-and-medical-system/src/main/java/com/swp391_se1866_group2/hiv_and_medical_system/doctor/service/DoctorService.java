package com.swp391_se1866_group2.hiv_and_medical_system.doctor.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.UserStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.DoctorMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request.DoctorCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request.DoctorUpdateDTORequest;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request.DoctorUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorAppointment;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorAppointmentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.repository.DoctorRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.repository.ScheduleSlotRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorService {
    DoctorRepository doctorRepository;
    UserService userService;
    DoctorMapper doctorMapper;
    ScheduleSlotRepository scheduleSlotRepository;

    PasswordEncoder passwordEncoder;

//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public DoctorResponse createDoctorAccount (DoctorCreationRequest request){
        if(userService.isEmailExisted(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED) ;
        }
        System.out.println(request);
        Doctor doctor = doctorMapper.toDoctor(request) ;
        User user = new User();
        user.setRole(Role.DOCTOR.name());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE.name());
        doctor.setUser(user);
        System.out.println(doctor);
        return doctorMapper.toDoctorResponse(doctorRepository.save(doctor));
    }

    public List<DoctorResponse> getAllDoctor (Pageable pageable){
        Slice<DoctorResponse> slice = doctorRepository.getAllDoctor(pageable).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED));
        return slice.getContent();
    }

    public DoctorResponse getDoctorResponseById(String id){
        return doctorRepository.getDoctorById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public DoctorResponse getDoctorProfileByToken(){
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        return doctorRepository.findDoctorByToken(email).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED));
    }

    public Doctor getDoctorResponseByToken(){
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        DoctorResponse doctorResponse = doctorRepository.findDoctorByToken(email).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED));
        return doctorRepository.findById(doctorResponse.getDoctorId()).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED));
    }

    public Doctor getDoctorById(String id){
        return doctorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED));
    }

    public DoctorResponse getDoctorByEmail(String email){
        return doctorRepository.findDoctorByUserEmail(email).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED));
    }

    public Doctor getDoctorResponseByEmail(String email) {
        DoctorResponse doctorResponse = doctorRepository.findDoctorByUserEmail(email).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED));
        return doctorRepository.findById(doctorResponse.getDoctorId()).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED));

    }


    public Doctor getDoctorEntityByEmail(String email){
        return doctorRepository.findDoctorEntityByUserEmail(email).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED));
    }

    public DoctorResponse updateDoctorProfile(String doctorId , DoctorUpdateRequest request) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED));
        doctorMapper.updateDoctor(request, doctor);
        return doctorMapper.toDoctorResponse(doctorRepository.save(doctor));
    }

    public List<DoctorResponse> getTopDoctorsForHome(){
        return new ArrayList<>(doctorRepository.getTopDoctor(PageRequest.of(0, 4)).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED)));
    }

    public DoctorResponse updateDoctorProfileByManager(String doctorId , DoctorUpdateDTORequest request) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED));
        doctorMapper.updateDoctorDTO(request, doctor);
        if(request.isActive()){
            doctor.getUser().setStatus(UserStatus.ACTIVE.name());
        }else{
            doctor.getUser().setStatus(UserStatus.INACTIVE.name());
        }
        return doctorMapper.toDoctorResponse(doctorRepository.save(doctor));
    }

    public long countDoctor(){
        return doctorRepository.count();
    }

    public long countDoctorActive(){
        return doctorRepository.countAllByUserStatus(UserStatus.ACTIVE.name());
    }

    public List<DoctorAppointmentResponse> getTopDoctorsAppointment(Pageable pageable){
        Slice<DoctorAppointment> doctors = scheduleSlotRepository.getTopDoctorByAppointmentCount(pageable);

        List<DoctorAppointmentResponse> doctorAppointmentResponses = new ArrayList<>();

        doctors.forEach(doctorAppointment -> {
            DoctorAppointmentResponse doctorAppointmentResponse = new DoctorAppointmentResponse();
            doctorAppointmentResponse.setDoctor(doctorMapper.toDoctorResponse(doctorAppointment.getDoctor()));
            doctorAppointmentResponse.setTotalAppointment(doctorAppointment.getTotalAppointment());
            doctorAppointmentResponses.add(doctorAppointmentResponse);
        });

        return doctorAppointmentResponses;

    }

    public String getDoctorImageUrl (String doctorId){
        return doctorRepository.getDocImageUrlByDoctorId(doctorId);
    }

}
