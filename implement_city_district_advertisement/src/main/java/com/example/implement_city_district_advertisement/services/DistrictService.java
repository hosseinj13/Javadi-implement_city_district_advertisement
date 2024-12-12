package com.example.implement_city_district_advertisement.services;

import com.example.implement_city_district_advertisement.Mappers.DistrictMapper;
import com.example.implement_city_district_advertisement.dto.DistrictDTO;
import com.example.implement_city_district_advertisement.exceptions.DuplicateEntityException;
import com.example.implement_city_district_advertisement.models.City;
import com.example.implement_city_district_advertisement.models.District;
import com.example.implement_city_district_advertisement.repositories.CityRepository;
import com.example.implement_city_district_advertisement.repositories.DistrictRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;

     // Creates a new district.
    public DistrictDTO createDistrict(DistrictDTO districtDTO) {
        log.info("Creating district with name: {} for city ID: {}", districtDTO.getDistrictName(), districtDTO.getCityId());
        City city = findCityById(districtDTO.getCityId());
        // Check for duplicate district name in the same city
        districtRepository.findByDistrictNameAndCityId(districtDTO.getDistrictName(), city.getId()).ifPresent(existingDistrict -> {
            throw new DuplicateEntityException("District with name '" + districtDTO.getDistrictName() + "' already exists in city ID: " + city.getId());
        });

        District district = DistrictMapper.INSTANCE.toDistrict(districtDTO);
        district.setCity(city);
        district.setCreatedAt(LocalDateTime.now());
        District savedDistrict = districtRepository.save(district);
        log.info("District created successfully with ID: {}", savedDistrict.getId());
        return DistrictMapper.INSTANCE.toDistrictDTO(savedDistrict);
    }

    //Updates an existing district.
    public DistrictDTO updateDistrict(Long id, DistrictDTO districtDTO) {
        log.info("Updating district with ID: {}", id);
        District district = findDistrictById(id);
        City city = findCityById(districtDTO.getCityId());

        // Check for duplicate district name in the same city (excluding the current district)
        districtRepository.findByDistrictNameAndCityId(districtDTO.getDistrictName(), city.getId()).ifPresent(existingDistrict -> {
            throw new DuplicateEntityException("District with name '" + districtDTO.getDistrictName() + "' already exists in city ID: " + city.getId());
        });

        district.setDistrictName(districtDTO.getDistrictName());
        district.setCity(city);
        District updatedDistrict = districtRepository.save(district);
        log.info("District with ID {} updated successfully.", id);
        return DistrictMapper.INSTANCE.toDistrictDTO(updatedDistrict);
    }

     // Deletes a district by its ID.
    public void deleteDistrict(Long id) {
        log.info("Deleting district with ID: {}", id);
        District district = findDistrictById(id);
        districtRepository.delete(district);
        log.info("District with ID {} deleted successfully.", id);
    }

     // Retrieves all districts by city ID.
    public List<DistrictDTO> getDistrictsByCity(Long cityId) {
        log.info("Fetching districts for city ID: {}", cityId);
        List<District> districts = districtRepository.findByCityId(cityId);
        if (districts.isEmpty()) {
            log.warn("No districts found for city ID: {}", cityId);
        }
        log.info("Retrieved {} districts for city ID: {}", districts.size(), cityId);
        return districts.stream()
                .map(DistrictMapper.INSTANCE::toDistrictDTO)
                .toList();
    }

    // --- Private Helper Methods ---
    private City findCityById(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(() ->
                new EntityNotFoundException("City not found with ID: " + cityId));
    }

    private District findDistrictById(Long districtId) {
        return districtRepository.findById(districtId).orElseThrow(() ->
                new EntityNotFoundException("District not found with ID: " + districtId));
    }
}

