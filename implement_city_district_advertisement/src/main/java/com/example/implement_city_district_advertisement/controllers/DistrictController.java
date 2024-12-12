package com.example.implement_city_district_advertisement.controllers;

import com.example.implement_city_district_advertisement.dto.DistrictDTO;
import com.example.implement_city_district_advertisement.services.DistrictService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
@RequiredArgsConstructor
@Slf4j
public class DistrictController {

    private final DistrictService districtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DistrictDTO createDistrict(@RequestBody @Valid DistrictDTO districtDTO) {
        log.info("Creating district: {}", districtDTO);
        return districtService.createDistrict(districtDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DistrictDTO updateDistrict(@PathVariable Long id, @RequestBody @Valid DistrictDTO districtDTO) {
        log.info("Updating district with ID: {}", id);
        return districtService.updateDistrict(id, districtDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDistrict(@PathVariable Long id) {
        log.info("Deleting district with ID: {}", id);
        districtService.deleteDistrict(id);
    }

    @GetMapping("/city/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    public List<DistrictDTO> getDistrictsByCity(@PathVariable Long cityId) {
        log.info("Fetching districts for city ID: {}", cityId);
        return districtService.getDistrictsByCity(cityId);
    }
}
