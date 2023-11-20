package com.pashonokk.marketplace.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDto {
    private Long id;
    private String name;
    private String description;
    private Set<ImageDto> images;
    private String location;
    private LocalDate creationDate;
    private boolean isActive = true;
}
