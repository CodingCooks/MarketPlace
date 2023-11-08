package com.pashonokk.marketplace.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryResponseDto {
    private Long id;
    private String name;
    private String description;
    private CategoryResponseDto category;
}
