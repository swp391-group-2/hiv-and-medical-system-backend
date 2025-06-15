package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.image.dto.response.ImageResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.image.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ImageMapper {
    @Mapping(source = "doctor.id", target = "doctorId")
    ImageResponse toImageResponse(Image image);
}
