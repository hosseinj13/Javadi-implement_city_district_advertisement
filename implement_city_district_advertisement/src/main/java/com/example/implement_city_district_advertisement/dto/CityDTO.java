package com.example.implement_city_district_advertisement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CityDTO {

     @NotBlank(message = "City name cannot be blank")
     @Size(max = 100, message = "City name cannot exceed 100 characters")
     String cityName;

     @NotNull(message = "Creation date must be provided")
     LocalDateTime createdAt;
}
