package com.example.implement_city_district_advertisement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DistrictDTO {

     @NotBlank(message = "District name cannot be blank")
     @Size(max = 100, message = "District name cannot exceed 100 characters")
     @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "District name can only contain letters and spaces")
     String districtName;

     @NotNull(message = "City ID must be provided")
     Long cityId;

     @NotNull(message = "Creation date must be provided")
     LocalDateTime createdAt;
}
