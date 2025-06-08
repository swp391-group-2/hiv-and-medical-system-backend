package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.schedule.dto.response.DoctorWorkScheduleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.dto.response.ScheduleSlotResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.entity.DoctorWorkSchedule;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.entity.ScheduleSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper( componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,uses = {DoctorMapper.class, SlotMapper.class})

public interface ScheduleMapper {
    @Mapping(target = "doctor", source = "doctor", qualifiedByName = "toDoctorResponse")
    DoctorWorkScheduleResponse toDoctorWorkScheduleResponse (DoctorWorkSchedule schedule);

    @Mapping(target = "slot", source = "slot", qualifiedByName = "toSlotResponse")
    ScheduleSlotResponse toScheduleSlotResponse (ScheduleSlot scheduleSlots);
}
