package com.example.implement_city_district_advertisement.repositories;

import com.example.implement_city_district_advertisement.models.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findByDistrictCityId(Long cityId);
    List<Advertisement> findByDistrictId(Long districtId);

    boolean existsByTitleAndIdNot(String title, Long id);

    boolean existsByTitle(String title);

}
