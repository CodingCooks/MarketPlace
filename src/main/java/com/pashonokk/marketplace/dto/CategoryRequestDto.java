package com.pashonokk.marketplace.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {
    private Long id;
    private String name;
    private String description;
}
