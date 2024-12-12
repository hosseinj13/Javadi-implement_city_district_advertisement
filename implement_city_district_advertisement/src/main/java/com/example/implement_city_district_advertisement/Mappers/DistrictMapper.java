package com.example.implement_city_district_advertisement.Mappers;

import com.example.implement_city_district_advertisement.dto.DistrictDTO;
import com.example.implement_city_district_advertisement.models.District;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DistrictMapper {

    DistrictMapper INSTANCE = Mappers.getMapper(DistrictMapper.class);

    DistrictDTO toDistrictDTO(District district);
    District toDistrict(DistrictDTO districtDTO);
}

