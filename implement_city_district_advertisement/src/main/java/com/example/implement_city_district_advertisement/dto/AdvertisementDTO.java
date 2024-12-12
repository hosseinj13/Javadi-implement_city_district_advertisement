package com.example.implement_city_district_advertisement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdvertisementDTO {

     @NotBlank(message = "Title cannot be blank")
     @Size(max = 100, message = "Title cannot exceed 100 characters")
     String title;

     @NotBlank(message = "Description cannot be blank")
     @Size(max = 1000, message = "Description cannot exceed 1000 characters")
     String description;

     @NotNull(message = "District ID must be provided")
     Long districtId;

     @NotNull(message = "User ID must be provided.")
     @Positive(message = "User ID must be a positive number.")
     Long userId;


     @NotNull(message = "Creation date must be provided")
     LocalDateTime createdAt;
}
