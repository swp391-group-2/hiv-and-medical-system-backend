package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response.*;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.DoctorWorkSchedule;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.ScheduleSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.dto.response.LabTestSlotResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.entity.LabTestSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper( componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,uses = {DoctorMapper.class, SlotMapper.class})

public interface ScheduleMapper {
    @Mapping(target = "doctor", source = "doctor", qualifiedByName = "toDoctorResponse")
    @Mapping(target = "scheduleSlots", source = "scheduleSlots")
    DoctorWorkScheduleResponse toDoctorWorkScheduleResponse (DoctorWorkSchedule schedule);

    @Mapping(target = "slot", source = "slot", qualifiedByName = "toSlotResponse")
    @Mapping(target = "scheduleId", source = "schedule.id")
    ScheduleSlotResponse toScheduleSlotResponse (ScheduleSlot scheduleSlots);

//    @Mapping(target = "slot", source = "slot", qualifiedByName = "toSlotResponse")
//    @Mapping(target = "scheduleId", source = "schedule.id")
//    ScheduleSlotDTOResponse toScheduleSlotDTOResponse (ScheduleSlot scheduleSlots);
    ScheduleResponse toScheduleResponse (DoctorWorkSchedule schedule);

    ScheduleSlotDTOResponse toScheduleDTOResponse (DoctorWorkSchedule schedule);

    LabTestSlotResponse toLabTestSlotResponse (LabTestSlot labTestSlot);


    ScheduleDTOResponse toScheduleDTOResponse (ScheduleResponse scheduleResponse);

    ScheduleSlotDateResponse toScheduleSlotDateResponse (ScheduleSlotResponse scheduleSlotResponse);


}
