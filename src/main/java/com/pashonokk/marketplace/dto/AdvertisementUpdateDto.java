package com.pashonokk.marketplace.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementUpdateDto {
    @NotBlank(message = "Advertisement name cannot be null or empty")
    private String name;
    @NotBlank(message = "Advertisement description cannot be null or empty")
    private String description;
    private String location;
}