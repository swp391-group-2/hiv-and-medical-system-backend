package com.swp391_se1866_group2.hiv_and_medical_system.dashboard.service;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.repository.AppointmentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.dashboard.dto.projection.MaxMinAppointmentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.dashboard.dto.query.AppointmentCountPerDate;
import com.swp391_se1866_group2.hiv_and_medical_system.dashboard.dto.response.*;
import com.swp391_se1866_group2.hiv_and_medical_system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class DashBoardService {

    UserRepository userRepository;
    AppointmentRepository appointmentRepository;

    private StatsResponse formatStats(String title, long currentValue, long previousValue, String suffix){
        String value = String.format("%,d", currentValue) + suffix;
        double percentValue;
        if(previousValue == 0){
            percentValue = (currentValue ==0) ? 0 :100;
        }
        else{
            percentValue = ((double) (currentValue-previousValue) / previousValue) *100;
        }

        String change = (percentValue >= 0 ? "+" : "") + String.format("%.0f", percentValue)  + "%";
        boolean isGrowing = percentValue > 0;
        return new StatsResponse(title, value, change, isGrowing);
    }

    public StatsResponse getTotalPatients(LocalDate startDate, LocalDate endDate, LocalDate now){

        long currentValue = userRepository.countPatients(startDate, now);
        long previousValue = userRepository.countPatients(startDate, endDate);
        return formatStats("Tổng khách hàng", currentValue, previousValue, " bệnh nhân");
    }

    public StatsResponse getTotalDoctors(LocalDate startDate, LocalDate endDate, LocalDate now){
        long currentValue = userRepository.countDoctors(startDate, now);
        long previousValue = userRepository.countDoctors(startDate, endDate);
        return formatStats("Bác sĩ hoạt động", currentValue, previousValue, " bác sĩ");
    }

    public StatsResponse getTotalTodayAppointment(LocalDate startDate, LocalDate endDate, LocalDate now){
        long currentValue = appointmentRepository.countAppointments(startDate, now);
        long previousValue = appointmentRepository.countAppointments(startDate, endDate);
        return formatStats("Lịch hẹn hôm nay", currentValue, previousValue, " lịch hẹn");
    }

    public List<StatsResponse> getAllStats(LocalDate startDate, LocalDate endDate, LocalDate now){
        return List.of(
                getTotalPatients(startDate, endDate, now),
                getTotalDoctors(startDate, endDate, now),
                getTotalTodayAppointment(startDate, endDate, now)
        );
    }

    public MaxMinAppointmentStatsResponse getMaxMinAppointment(){
        Optional<MaxMinAppointmentResponse> maxAppointment = appointmentRepository.findMaxAppointmentPerDay();
        Optional<MaxMinAppointmentResponse> minAppointment = appointmentRepository.findMinAppointmentPerDay();

        long maxCount = 0;
        LocalDate maxDate = null;
        if(maxAppointment.isPresent()){
            maxCount = maxAppointment.get().getCount();
            maxDate = maxAppointment.get().getDate();
        }

        long minCount =0;
        LocalDate minDate= null;
        if(minAppointment.isPresent()){
            minCount=minAppointment.get().getCount();
            minDate = minAppointment.get().getDate();
        }

        return MaxMinAppointmentStatsResponse.builder()
                .maxAppointments(maxCount)
                .maxAppointmentsDate(maxDate)
                .minAppointments(minCount)
                .minAppointmentsDate(minDate)
                .build();
    }

    public AverageAppointmentsResponse getAverageAppointment(){
        Double averageAppointmentsPerDay = appointmentRepository.findAverageAppointmentsPerDay();
        if (averageAppointmentsPerDay == null){
            averageAppointmentsPerDay = 0.0;
        }
        return new AverageAppointmentsResponse(averageAppointmentsPerDay);
    }

    public CancelledAppointmentResponse cancelledAppointment(){
        long totalAppointments = appointmentRepository.countTotalAppointments();
        long cancelledAppointments = appointmentRepository.countCancelledAppointments();
        double cancelledValue = 0.0;
        if (totalAppointments > 0){
            cancelledValue = (cancelledAppointments / (double) totalAppointments) *100;
        }
        return new CancelledAppointmentResponse(cancelledValue);
    }

    public AppointmentStatisticResponse getAllAppointmentStatistics(){
        return AppointmentStatisticResponse.builder()
                .averageAppointments(getAverageAppointment())
                .maxMinAppointmentStats(getMaxMinAppointment())
                .cancelledAppointment(cancelledAppointment())
                .build();
    }

}
