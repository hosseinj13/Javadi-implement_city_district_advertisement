package com.example.implement_city_district_advertisement.controllers;

import com.example.implement_city_district_advertisement.dto.CityDTO;
import com.example.implement_city_district_advertisement.services.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
@Slf4j
public class CityController {

    private final CityService cityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityDTO createCity(@RequestBody @Valid CityDTO cityDTO) {
        log.info("Creating city: {}", cityDTO);
        return cityService.createCity(cityDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CityDTO updateCity(@PathVariable Long id, @RequestBody @Valid CityDTO cityDTO) {
        log.info("Updating city with ID: {}", id);
        return cityService.updateCity(id, cityDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable Long id) {
        log.info("Deleting city with ID: {}", id);
        cityService.deleteCity(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CityDTO> getAllCities() {
        log.info("Fetching all cities");
        return cityService.getAllCities();
    }
}
