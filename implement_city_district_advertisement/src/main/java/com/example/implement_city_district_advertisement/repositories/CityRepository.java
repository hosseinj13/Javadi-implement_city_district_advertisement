package com.example.implement_city_district_advertisement.repositories;

import com.example.implement_city_district_advertisement.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCityName(String name);
}
