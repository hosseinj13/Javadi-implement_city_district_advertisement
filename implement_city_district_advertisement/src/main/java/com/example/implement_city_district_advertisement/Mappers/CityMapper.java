package com.example.implement_city_district_advertisement.Mappers;

import com.example.implement_city_district_advertisement.dto.CityDTO;
import com.example.implement_city_district_advertisement.models.City;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    CityDTO toCityDTO(City city);
    City toCity(CityDTO cityDTO);
}

