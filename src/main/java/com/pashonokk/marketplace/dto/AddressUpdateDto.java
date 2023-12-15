package com.pashonokk.marketplace.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdateDto {
    private String city;
    private String fullAddress;
    private String country;
    private Integer postalCode;
}
