package com.example.implement_city_district_advertisement.repositories;

import com.example.implement_city_district_advertisement.models.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {

    List<District> findByCityId(Long cityId);

    Optional<Object> findByDistrictNameAndCityId(String districtName, Long id);

}
