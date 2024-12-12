package com.example.implement_city_district_advertisement.controllers;

import com.example.implement_city_district_advertisement.dto.AdvertisementDTO;
import com.example.implement_city_district_advertisement.services.AdvertisementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advertisements")
@RequiredArgsConstructor
@Slf4j
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertisementDTO createAdvertisement(@RequestBody @Valid AdvertisementDTO advertisementDTO) {
        log.info("Creating advertisement: {}", advertisementDTO);
        return advertisementService.createAdvertisement(advertisementDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdvertisementDTO updateAdvertisement(@PathVariable Long id, @RequestBody @Valid AdvertisementDTO advertisementDTO) {
        log.info("Updating advertisement with ID: {}", id);
        return advertisementService.updateAdvertisement(id, advertisementDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdvertisement(@PathVariable Long id) {
        log.info("Deleting advertisement with ID: {}", id);
        advertisementService.deleteAdvertisement(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AdvertisementDTO> getAdvertisements(
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long districtId) {
        log.info("Fetching advertisements for cityId: {}, districtId: {}", cityId, districtId);
        return advertisementService.getAdvertisements(cityId, districtId);
    }
}
