package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.blog.dto.request.BlogCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.blog.dto.request.BlogUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.blog.dto.response.BlogResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.blog.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BlogMapper {
    @Mapping(source = "id", target = "blogId")
    BlogResponse toBlogResponse(Blog blog);
    Blog toBlog(BlogCreationRequest blogRequest);

    void updateBlog(@MappingTarget Blog blog, BlogUpdateRequest blogUpdateRequest);
}
