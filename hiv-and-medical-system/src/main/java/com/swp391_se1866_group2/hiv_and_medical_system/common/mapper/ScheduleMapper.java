package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response.DoctorWorkScheduleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response.ScheduleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response.ScheduleSlotResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.DoctorWorkSchedule;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.ScheduleSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.dto.response.LabTestSlotResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.entity.LabTestSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper( componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,uses = {DoctorMapper.class, SlotMapper.class})

public interface ScheduleMapper {
    @Mapping(target = "doctor", source = "doctor", qualifiedByName = "toDoctorResponse")
    DoctorWorkScheduleResponse toDoctorWorkScheduleResponse (DoctorWorkSchedule schedule);

    @Mapping(target = "slot", source = "slot", qualifiedByName = "toSlotResponse")
    ScheduleSlotResponse toScheduleSlotResponse (ScheduleSlot scheduleSlots);

    ScheduleResponse toScheduleResponse (DoctorWorkSchedule schedule);

    LabTestSlotResponse toLabTestSlotResponse (LabTestSlot labTestSlot);
}
