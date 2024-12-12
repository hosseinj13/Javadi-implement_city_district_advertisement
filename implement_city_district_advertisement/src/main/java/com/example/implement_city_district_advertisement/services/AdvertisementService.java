package com.example.implement_city_district_advertisement.services;

import com.example.implement_city_district_advertisement.Mappers.AdvertisementMapper;
import com.example.implement_city_district_advertisement.dto.AdvertisementDTO;
import com.example.implement_city_district_advertisement.exceptions.DuplicateEntityException;
import com.example.implement_city_district_advertisement.models.Advertisement;
import com.example.implement_city_district_advertisement.models.District;
import com.example.implement_city_district_advertisement.repositories.AdvertisementRepository;
import com.example.implement_city_district_advertisement.repositories.DistrictRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final DistrictRepository districtRepository;

     // Creates a new Advertisement with the given AdvertisementDTO.
    public AdvertisementDTO createAdvertisement(@Valid AdvertisementDTO advertisementDTO) {
        log.info("Creating advertisement with title: {}", advertisementDTO.getTitle());
        validateDuplicateTitle(advertisementDTO.getTitle());
        District district = findDistrictById(advertisementDTO.getDistrictId());
        Advertisement advertisement = AdvertisementMapper.INSTANCE.toAdvertisement(advertisementDTO);
        advertisement.setDistrict(district);
        advertisement.setCreatedAt(LocalDateTime.now());
        Advertisement savedAdvertisement = advertisementRepository.save(advertisement);
        log.info("Advertisement created successfully with ID: {}", savedAdvertisement.getId());
        return AdvertisementMapper.INSTANCE.toAdvertisementDTO(savedAdvertisement);
    }

     // Updates an existing Advertisement by its ID and the given AdvertisementDTO.
    public AdvertisementDTO updateAdvertisement(Long id, @Valid AdvertisementDTO advertisementDTO) {
        log.info("Updating advertisement with ID: {}", id);
        Advertisement advertisement = findAdvertisementById(id);
        validateDuplicateTitleExcludingCurrent(advertisementDTO.getTitle(), id);
        District district = findDistrictById(advertisementDTO.getDistrictId());
        advertisement.setTitle(advertisementDTO.getTitle());
        advertisement.setDescription(advertisementDTO.getDescription());
        advertisement.setDistrict(district);
        Advertisement updatedAdvertisement = advertisementRepository.save(advertisement);
        log.info("Advertisement updated successfully with ID: {}", updatedAdvertisement.getId());
        return AdvertisementMapper.INSTANCE.toAdvertisementDTO(updatedAdvertisement);
    }


     // Deletes an Advertisement by its ID.
    public void deleteAdvertisement(Long id) {
        log.info("Deleting advertisement with ID: {}", id);
        Advertisement advertisement = findAdvertisementById(id);
        advertisementRepository.delete(advertisement);
        log.info("Advertisement deleted successfully with ID: {}", id);
    }

     // Retrieves advertisements by optional city ID or district ID.
    public List<AdvertisementDTO> getAdvertisements(Long cityId, Long districtId) {
        log.info("Fetching advertisements for cityId: {}, districtId: {}", cityId, districtId);
        validateFilters(cityId, districtId);
        List<Advertisement> advertisements = fetchAdvertisements(cityId, districtId);
        if (advertisements.isEmpty()) {
            log.warn("No advertisements found for the given filters.");
        }
        return advertisements.stream()
                .map(AdvertisementMapper.INSTANCE::toAdvertisementDTO)
                .toList();
    }

    // --- Private Helper Methods ---
    private void validateDuplicateTitle(String title) {
        if (advertisementRepository.existsByTitle(title)) {
            throw new DuplicateEntityException("Advertisement with title already exists: " + title);
        }
    }

    private void validateDuplicateTitleExcludingCurrent(String title, Long id) {
        if (advertisementRepository.existsByTitleAndIdNot(title, id)) {
            throw new DuplicateEntityException("Advertisement with title already exists: " + title);
        }
    }

    private District findDistrictById(Long districtId) {
        return districtRepository.findById(districtId)
                .orElseThrow(() -> new EntityNotFoundException("District not found with ID: " + districtId));
    }

    private Advertisement findAdvertisementById(Long id) {
        return advertisementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement not found with ID: " + id));
    }

    private void validateFilters(Long cityId, Long districtId) {
        if (cityId != null && districtId != null) {
            throw new IllegalArgumentException("Cannot filter by both cityId and districtId simultaneously.");
        }
    }

    private List<Advertisement> fetchAdvertisements(Long cityId, Long districtId) {
        if (cityId != null) {
            return advertisementRepository.findByDistrictCityId(cityId);
        } else if (districtId != null) {
            return advertisementRepository.findByDistrictId(districtId);
        } else {
            return advertisementRepository.findAll();
        }
    }
}
