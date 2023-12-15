package com.pashonokk.marketplace.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressSavingDto {
    @NotBlank(message = "City`s name can`t be empty or null")
    private String city;
    @NotBlank(message = "Full address field can`t be empty or null")
    private String fullAddress;
    @NotBlank(message = "Country`s name can`t be empty or null")
    private String country;
    @Range(min = 1000, max = 99999, message = "Postal code must be between 1000 and 99999")
    @NotNull(message = "Postal code can`t be null")
    private int postalCode;
}
