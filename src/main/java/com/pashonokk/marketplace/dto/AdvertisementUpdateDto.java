package com.pashonokk.marketplace.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementUpdateDto {
    private String name;
    private String description;
    private String location;
    private AddressUpdateDto address;
}