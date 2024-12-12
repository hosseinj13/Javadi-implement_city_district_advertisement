package com.example.implement_city_district_advertisement.Mappers;

import com.example.implement_city_district_advertisement.dto.AdvertisementDTO;
import com.example.implement_city_district_advertisement.models.Advertisement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {

    AdvertisementMapper INSTANCE = Mappers.getMapper(AdvertisementMapper.class);

    AdvertisementDTO toAdvertisementDTO(Advertisement advertisement);
    Advertisement toAdvertisement(AdvertisementDTO advertisementDTO);
}
