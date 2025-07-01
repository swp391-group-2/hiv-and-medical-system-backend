package com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.ScheduleMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.service.DoctorService;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.request.ScheduleCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.request.ScheduleUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response.*;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.DoctorWorkSchedule;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.ScheduleSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.repository.DoctorWorkScheduleRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.repository.ScheduleSlotRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.service.SlotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorWorkScheduleService {
    DoctorWorkScheduleRepository doctorWorkScheduleRepository;
    ScheduleSlotRepository scheduleSlotRepository;
    SlotService slotService;
    DoctorService doctorService;
    ScheduleMapper scheduleMapper;

    //    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') ")
    public DoctorWorkScheduleResponse createDoctorSchedule (String doctorId , ScheduleCreationRequest request) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if(doctorWorkScheduleRepository.existsByWorkDateAndDoctorId(request.getWorkDate(), doctorId)){
            throw new AppException(ErrorCode.WORK_DATE_EXISTED);
        }
        DoctorWorkSchedule schedule = new DoctorWorkSchedule();
        schedule.setDoctor(doctor);
        schedule.setWorkDate(request.getWorkDate());
        List<ScheduleSlot> scheduleSlots = request.getSlotId().stream()
                .map(slotId -> {
                    ScheduleSlot scheduleSlot = new ScheduleSlot();
                    scheduleSlot.setSlot(slotService.getSlotById(slotId));
                    scheduleSlot.setSchedule(schedule);
                    return scheduleSlot;
                })
                .collect(Collectors.toList());
        schedule.setScheduleSlots(scheduleSlots);
        DoctorWorkSchedule doctorWorkSchedule = doctorWorkScheduleRepository.save(schedule);
        return scheduleMapper.toDoctorWorkScheduleResponse(doctorWorkSchedule);
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public DoctorWorkScheduleResponse updateDoctorSchedule (String doctorId, ScheduleUpdateRequest request, LocalDate date) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        DoctorWorkSchedule schedule = doctorWorkScheduleRepository.findByWorkDate(date)
                .orElseThrow(() -> new AppException(ErrorCode.WORK_DATE_NOT_EXISTED));
        List<ScheduleSlot> scheduleSlots = request.getSlotId().stream()
                .map(slotId -> {
                    ScheduleSlot scheduleSlot = new ScheduleSlot();
                    scheduleSlot.setSlot(slotService.getSlotById(slotId));
                    scheduleSlot.setSchedule(schedule);
                    return scheduleSlot;
                })
                .collect(Collectors.toList());
        schedule.setScheduleSlots(scheduleSlots);
        return scheduleMapper.toDoctorWorkScheduleResponse(doctorWorkScheduleRepository.save(schedule));
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public List<DoctorWorkScheduleResponse> createDoctorScheduleBulk (String doctorId, ScheduleCreationRequest request) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        LocalDate start = request.getWorkDate();
        LocalDate end = request.getWorkDate().plusDays(7);
        List<DoctorWorkScheduleResponse> doctorWorkSchedules = new ArrayList<>();
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (!doctorWorkScheduleRepository.existsByWorkDateAndDoctorId(date, doctorId)) {
                request.setWorkDate(date);
                doctorWorkSchedules.add(createDoctorSchedule(doctorId, request));
            }
        }
        return doctorWorkSchedules;
    }

    public ScheduleDTOResponse getDoctorWorkScheduleByDate (String doctorId, LocalDate workDate) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        DoctorWorkSchedule doctorWorkSchedules = doctorWorkScheduleRepository.findAllByWorkDateAndDoctorId(workDate, doctorId);

        ScheduleResponse scheduleResponse = scheduleMapper.toScheduleResponse(doctorWorkSchedules);

        ScheduleDTOResponse scheduleDTOResponse = scheduleMapper.toScheduleDTOResponse(scheduleResponse);

        if(scheduleDTOResponse != null){
            ScheduleDTOResponse finalScheduleDTOResponse = scheduleDTOResponse;
            scheduleDTOResponse.getScheduleSlots().forEach(
                    scheduleSlotDateResponse -> {
                        if(scheduleSlotDateResponse != null) {
                            scheduleSlotDateResponse.setDate(finalScheduleDTOResponse.getWorkDate());
                        }
                    }
            );
        }else {
            ScheduleDTOResponse scheduleDTOResponseTmp = new ScheduleDTOResponse();
            scheduleDTOResponseTmp.setWorkDate(workDate);
            scheduleDTOResponseTmp.setScheduleSlots(new HashSet<>());
            return scheduleDTOResponseTmp;
        }

        return scheduleDTOResponse;
    }

    public List<ScheduleResponse> getDoctorWorkScheduleByDoctorId (String doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        List<DoctorWorkSchedule> doctorWorkSchedules = doctorWorkScheduleRepository.findAllByDoctorId(doctorId);
        return doctorWorkSchedules.stream().map(scheduleMapper::toScheduleResponse).collect(Collectors.toList());
    }

//    @PreAuthorize("hasRole('DOCTOR')")
    public List<ScheduleResponse> getDWScheduleByTokenAndBetweenDate (LocalDate startTime, LocalDate endTime) {
        DoctorResponse doctor = doctorService.getDoctorProfileByToken();
        if(startTime == null && endTime == null) {
            throw new AppException(ErrorCode.DATE_INPUT_INVALID);
        }
        List<DoctorWorkSchedule> listDWSchedule = doctorWorkScheduleRepository.findAllByWorkDateBetweenAndDoctorId(startTime, endTime, doctor.getDoctorId());
        return listDWSchedule.stream().map(scheduleMapper::toScheduleResponse).collect(Collectors.toList());
    }

    public List<DoctorWorkScheduleResponse> getAllDoctorWorkSchedule () {
        return doctorWorkScheduleRepository.findAll().stream().map(scheduleMapper::toDoctorWorkScheduleResponse).collect(Collectors.toList());
    }

    public List<ScheduleResponse> getWeekDWScheduleByDoctorIdAndDate (String doctorId, LocalDate date) {
        DoctorResponse doctor = doctorService.getDoctorResponseById(doctorId);
        LocalDate startTime = date.with(DayOfWeek.MONDAY);
        LocalDate endTime = date.with(DayOfWeek.SUNDAY);
        List<DoctorWorkSchedule> listDWSchedule = doctorWorkScheduleRepository.findAllByWorkDateBetweenAndDoctorId(startTime, endTime, doctor.getDoctorId());
        List<ScheduleResponse> scheduleResponseList = new  ArrayList<>();

        AtomicBoolean isEqualDate = new AtomicBoolean(false);

        for (LocalDate dateTmp = startTime ; !dateTmp.isAfter(endTime); dateTmp = dateTmp.plusDays(1)) {
            LocalDate finalDateTmp = dateTmp;
            isEqualDate.set(false);
            listDWSchedule.forEach(schedule -> {
                if(schedule.getWorkDate().equals(finalDateTmp)){
                    scheduleResponseList.add(scheduleMapper.toScheduleResponse(schedule));
                    isEqualDate.set(true);
                }
            });
            if(!isEqualDate.get()) {
                ScheduleResponse scheduleResponse = new ScheduleResponse();
                scheduleResponse.setWorkDate(dateTmp);
                scheduleResponse.setScheduleSlots(new HashSet<>());
                scheduleResponseList.add(scheduleResponse);
            }
        }

        return scheduleResponseList;

    }


    public List<DoctorWorkScheduleResponse> generateDoctorSchedule (String doctorId , List<ScheduleCreationRequest> request){
        List<DoctorWorkScheduleResponse> doctorWorkScheduleResponses = new ArrayList<>();

        if(request!= null){
            request.forEach(scheduleCreationRequest -> {
                if(scheduleCreationRequest.getSlotId() != null && !scheduleCreationRequest.getSlotId().isEmpty()){
                    doctorWorkScheduleResponses.add(createDoctorSchedule(doctorId, scheduleCreationRequest));
                }
            });
        }
        return doctorWorkScheduleResponses;
    }


    public DoctorWorkScheduleResponse generateDoctorScheduleCustom (String doctorId , ScheduleCreationRequest request) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        DoctorWorkSchedule schedule = new DoctorWorkSchedule();
        if(doctorWorkScheduleRepository.existsByWorkDateAndDoctorId(request.getWorkDate(), doctorId)){
            schedule = doctorWorkScheduleRepository.findByWorkDateAndDoctorId(request.getWorkDate(), doctorId);
        }
        schedule.setDoctor(doctor);
        schedule.setWorkDate(request.getWorkDate());
        DoctorWorkSchedule finalSchedule = schedule;
        List<ScheduleSlot> scheduleSlots = request.getSlotId().stream()
                .map(slotId -> {
                    ScheduleSlot scheduleSlot = scheduleSlotRepository.findScheduleSlotBySlotIdAndDoctorWorkScheduleId(slotId, finalSchedule.getId());
                    if(scheduleSlot != null){
                        return scheduleSlot;

                    }else{
                        scheduleSlot = new ScheduleSlot();
                        scheduleSlot.setSlot(slotService.getSlotById(slotId));
                        scheduleSlot.setSchedule(finalSchedule);
                        return scheduleSlot;
                    }
                })
                .collect(Collectors.toList());
        schedule.setScheduleSlots(scheduleSlots);
        DoctorWorkSchedule doctorWorkSchedule = doctorWorkScheduleRepository.save(schedule);
        return scheduleMapper.toDoctorWorkScheduleResponse(doctorWorkSchedule);
    }



}
