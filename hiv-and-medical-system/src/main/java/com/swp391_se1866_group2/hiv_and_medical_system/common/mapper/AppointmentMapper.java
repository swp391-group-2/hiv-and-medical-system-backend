package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response.AppointmentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import org.hibernate.LazyInitializationException;
import org.mapstruct.*;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.time.LocalTime;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppointmentMapper {

    @Mapping(target = "appointmentId", source = "id")
    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "serviceId", source = "service.id")
    @Mapping(target = "serviceName", source = "service.name")
    @Mapping(target = "serviceType", source = "service.serviceType")
    @Mapping(target = "price", source = "service.price")
    @Mapping(target = "labTestSlotId", source = "labTestSlot.id")
    @Mapping(target = "scheduleSlotId", source = "scheduleSlot.id")
    @Mapping(target = "prescriptionId", source = "prescription.id")
    @Mapping(target = "labSampleId", source = "labSample.id")
    @Mapping(target = "doctorName", expression = "java(getDoctorName(appointment))")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "startTime", expression = "java(getStartTime(appointment))")
    @Mapping(target = "endTime", expression = "java(getEndTime(appointment))")
    @Mapping(target = "slotDescription", expression = "java(getSlotDescription(appointment))")
    @Mapping(target = "date", expression = "java(getAppointmentDate(appointment))")
    AppointmentResponse toAppointmentResponse(Appointment appointment);

    default String getDoctorName(Appointment appointment) {
        try {
            if (appointment != null && appointment.getScheduleSlot() != null) {
                var slot = appointment.getScheduleSlot();
                try {
                    if (slot.getSchedule() != null) {
                        var schedule = slot.getSchedule();
                        try {
                            if (schedule.getDoctor() != null) {
                                var doctor = schedule.getDoctor();
                                try {
                                    if (doctor.getUser() != null) {
                                        return doctor.getUser().getFullName();
                                    }
                                } catch (LazyInitializationException e) {
                                }
                            }
                        } catch (LazyInitializationException e) {
                        }
                    }
                } catch (LazyInitializationException e) {
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    default LocalTime getStartTime(Appointment appointment) {
        try {
            if (appointment != null) {
                if (appointment.getScheduleSlot() != null) {
                    try {
                        if (appointment.getScheduleSlot().getSlot() != null) {
                            return appointment.getScheduleSlot().getSlot().getStartTime();
                        }
                    } catch (LazyInitializationException e) {
                    }
                } else if (appointment.getLabTestSlot() != null) {
                    try {
                        if (appointment.getLabTestSlot().getSlot() != null) {
                            return appointment.getLabTestSlot().getSlot().getStartTime();
                        }
                    } catch (LazyInitializationException e) {
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    default LocalTime getEndTime(Appointment appointment) {
        try {
            if (appointment != null) {
                if (appointment.getScheduleSlot() != null) {
                    try {
                        if (appointment.getScheduleSlot().getSlot() != null) {
                            return appointment.getScheduleSlot().getSlot().getEndTime();
                        }
                    } catch (LazyInitializationException e) {
                    }
                } else if (appointment.getLabTestSlot() != null) {
                    try {
                        if (appointment.getLabTestSlot().getSlot() != null) {
                            return appointment.getLabTestSlot().getSlot().getEndTime();
                        }
                    } catch (LazyInitializationException e) {
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    default String getSlotDescription(Appointment appointment) {
        try {
            if (appointment != null) {
                if (appointment.getScheduleSlot() != null) {
                    try {
                        if (appointment.getScheduleSlot().getSlot() != null) {
                            return appointment.getScheduleSlot().getSlot().getDescription();
                        }
                    } catch (LazyInitializationException e) {
                    }
                } else if (appointment.getLabTestSlot() != null) {
                    try {
                        if (appointment.getLabTestSlot().getSlot() != null) {
                            return appointment.getLabTestSlot().getSlot().getDescription();
                        }
                    } catch (LazyInitializationException e) {
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    default LocalDate getAppointmentDate(Appointment appointment) {
        try {
            if (appointment != null) {
                if (appointment.getScheduleSlot() != null) {
                    try {
                        if (appointment.getScheduleSlot().getSchedule() != null) {
                            return appointment.getScheduleSlot().getSchedule().getWorkDate();
                        }
                    } catch (LazyInitializationException e) {
                    }
                } else if (appointment.getLabTestSlot() != null) {
                    try {
                        return appointment.getLabTestSlot().getDate();
                    } catch (LazyInitializationException e) {
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
}
