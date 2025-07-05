package com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.expire;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.LabTestStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.entity.LabTestSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.repository.LabTestSlotRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LabTestSlotExpireService {
    LabTestSlotRepository labTestSlotRepository;

    @Scheduled(fixedDelay = 600000)
    public void expiredLabTestSlot(){
        LocalDateTime today = LocalDateTime.now();
        labTestSlotRepository.findAllLabTestSlotExpiredByDate(today.toLocalDate())
                .forEach(labTestSlot -> {
                    if(labTestSlot.getDate().isBefore(today.toLocalDate())){
                        labTestSlot.setStatus(LabTestStatus.EXPIRED);
                    }else if(labTestSlot.getDate().equals(today.toLocalDate()) &&
                            labTestSlot.getSlot().getStartTime().isBefore(today.toLocalTime())){
                        labTestSlot.setStatus(LabTestStatus.EXPIRED);
                    }
                    labTestSlotRepository.save(labTestSlot);
                 });
    }
}
