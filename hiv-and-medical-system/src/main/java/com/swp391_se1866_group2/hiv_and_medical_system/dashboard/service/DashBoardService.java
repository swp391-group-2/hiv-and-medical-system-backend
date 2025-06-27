package com.swp391_se1866_group2.hiv_and_medical_system.dashboard.service;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.repository.AppointmentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.dashboard.dto.projection.MaxMinAppointmentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.dashboard.dto.response.*;
import com.swp391_se1866_group2.hiv_and_medical_system.payment.repository.PaymentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashBoardService {

    UserRepository userRepository;
    AppointmentRepository appointmentRepository;
    PaymentRepository paymentRepository;

    private StatsResponse formatStats(String title, long currentValue, long previousValue){
        String value = String.format("%,d", currentValue);

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

    public StatsResponse getTotalPatients(LocalDate milestone){
        long previousValue;
        long currentValue = userRepository.countTotalPatients();

        LocalDate today = LocalDate.now();
        if(!milestone.isBefore(today)){
            previousValue = currentValue;
        }

        else {
            previousValue = userRepository.countPatients(milestone);
        }
        return formatStats("Tổng khách hàng", currentValue, previousValue);
    }

    public StatsResponse getTotalDoctors(LocalDate milestone){
        long previousValue;
        long currentValue = userRepository.countTotalDoctors();

        LocalDate today = LocalDate.now();
        if(!milestone.isBefore(today)){
            previousValue = currentValue;
        }

        else {
            previousValue = userRepository.countDoctors(milestone);
        }

        return formatStats("Bác sĩ hoạt động", currentValue, previousValue);
    }

    public StatsResponse getTotalTodayAppointment(LocalDate milestone){
        long previousValue;
        long currentValue = appointmentRepository.countTotalAppointments();

        LocalDate today = LocalDate.now();
        if(!milestone.isBefore(today)){
            previousValue = currentValue;
        }

        else {
            previousValue = appointmentRepository.countAppointments(milestone);
        }

        return formatStats("Tổng lịch hẹn", currentValue, previousValue);
    }

    public StatsResponse getTotalRevenue(LocalDate milestone){
        long previousValue;
        long currentValue = paymentRepository.getTotalRevenue();
        LocalDate today = LocalDate.now();
        if(!milestone.isBefore(today)){
            previousValue = currentValue;
        }

        else {
            previousValue = paymentRepository.getRevenue(milestone);
        }

        return formatStats("Doanh thu tháng", currentValue, previousValue);
    }


    public List<StatsResponse> getAllStats(LocalDate milestone){
        return List.of(
                getTotalPatients(milestone),
                getTotalDoctors(milestone),
                getTotalTodayAppointment(milestone),
                getTotalRevenue(milestone)
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

        String formatCancelledValue;
        if(totalAppointments == 0){
            formatCancelledValue = "0%";
        }
        else {
            double cancelledValue = ((double)cancelledAppointments /  totalAppointments) *100;
            formatCancelledValue = String.format("%.0f", cancelledValue)  + "%";
        }

        return new CancelledAppointmentResponse(formatCancelledValue);
    }

    public AppointmentStatisticResponse getAllAppointmentStatistics(){
        return AppointmentStatisticResponse.builder()
                .averageAppointments(getAverageAppointment())
                .maxMinAppointmentStats(getMaxMinAppointment())
                .cancelledAppointment(cancelledAppointment())
                .build();
    }

    public ServiceAppointmentStats getConsultationAppointments(){
        long consultationAppointments = appointmentRepository.countConsultationAppointments();
        long totalAppointments = appointmentRepository.countTotalAppointments();

        String formatConsultationValue;
        if (totalAppointments == 0){
            formatConsultationValue = "0";
        }

        else {
            double consultationValue = ((double) consultationAppointments / totalAppointments) *100;
            formatConsultationValue = String.format("%.0f", consultationValue);
        }

        return new ServiceAppointmentStats("CONSULTATION", consultationAppointments, formatConsultationValue);
    }

    public ServiceAppointmentStats getScreeningAppointments(){
        long screeningAppointments = appointmentRepository.countScreeningAppointments();
        long totalAppointments = appointmentRepository.countTotalAppointments();

        String formatScreeningValue;
        if (totalAppointments == 0){
            formatScreeningValue = "0";
        }

        else {
            double screeningValue = ((double) screeningAppointments / totalAppointments) *100;
            formatScreeningValue =  String.format("%.0f", screeningValue);
        }

        return new ServiceAppointmentStats("SCREENING", screeningAppointments, formatScreeningValue);
    }

    public ServiceAppointmentStats getConfirmatoryAppointments(){
        long confirmatoryAppointments = appointmentRepository.countConfirmatoryAppointments();
        long totalAppointments = appointmentRepository.countTotalAppointments();

        String formatConfirmatoryValue;
        if (totalAppointments == 0){
            formatConfirmatoryValue = "0";
        }

        else {
            double confirmatoryValue = ((double) confirmatoryAppointments / totalAppointments) *100;
            formatConfirmatoryValue =  String.format("%.0f", confirmatoryValue);
        }

        return new ServiceAppointmentStats("CONFIRMATORY", confirmatoryAppointments, formatConfirmatoryValue);
    }

    public List<ServiceAppointmentStats> getAllServiceAppointmentStats(){
        return List.of(
                getConsultationAppointments(),
                getScreeningAppointments(),
                getConfirmatoryAppointments()
        );
    }



}
