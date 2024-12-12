package com.example.implement_city_district_advertisement.services;

import com.example.implement_city_district_advertisement.Mappers.CityMapper;
import com.example.implement_city_district_advertisement.dto.CityDTO;
import com.example.implement_city_district_advertisement.exceptions.DuplicateEntityException;
import com.example.implement_city_district_advertisement.models.City;
import com.example.implement_city_district_advertisement.repositories.CityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityService {

    private final CityRepository cityRepository;

     // Creates a new city.
    public CityDTO createCity(CityDTO cityDTO) {
        log.info("Creating city with name: {}", cityDTO.getCityName());
        validateCityNameUniqueness(cityDTO.getCityName());
        City city = CityMapper.INSTANCE.toCity(cityDTO);
        city.setCreatedAt(LocalDateTime.now());
        City savedCity = cityRepository.save(city);
        log.info("City created successfully with ID: {}", savedCity.getId());
        return CityMapper.INSTANCE.toCityDTO(savedCity);
    }


     // Updates an existing city by its ID.
    public CityDTO updateCity(Long id, CityDTO cityDTO) {
        log.info("Updating city with ID: {}", id);
        City city = findCityById(id);
        validateCityNameUniquenessForUpdate(cityDTO.getCityName(), id);
        city.setCityName(cityDTO.getCityName());
        City updatedCity = cityRepository.save(city);
        log.info("City with ID {} updated successfully.", id);
        return CityMapper.INSTANCE.toCityDTO(updatedCity);
    }

     // Deletes a city by its ID.
    public void deleteCity(Long id) {
        log.info("Deleting city with ID: {}", id);
        City city = findCityById(id);
        cityRepository.delete(city);
        log.info("City with ID {} deleted successfully.", id);
    }

     // Retrieves all cities.
    public List<CityDTO> getAllCities() {
        log.info("Fetching all cities.");
        List<City> cities = cityRepository.findAll();
        if (cities.isEmpty()) {
            log.warn("No cities found.");
        }
        log.info("Retrieved {} cities.", cities.size());
        return cities.stream()
                .map(CityMapper.INSTANCE::toCityDTO)
                .toList();
    }

    // --- Private Helper Methods ---

     // Validates the uniqueness of the city name for creation.
    private void validateCityNameUniqueness(String cityName) {
        cityRepository.findByCityName(cityName).ifPresent(city -> {
            throw new DuplicateEntityException("City with the given name already exists: " + cityName);
        });
    }

     // Validates the uniqueness of the city name for update.
    private void validateCityNameUniquenessForUpdate(String cityName, Long cityId) {
        cityRepository.findByCityName(cityName).ifPresent(existingCity -> {
            if (!existingCity.getId().equals(cityId)) {
                throw new DuplicateEntityException("City with the given name already exists: " + cityName);
            }
        });
    }

     // Finds a city by its ID.
    private City findCityById(Long id) {
        return cityRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("City not found with ID: " + id));
    }
}
