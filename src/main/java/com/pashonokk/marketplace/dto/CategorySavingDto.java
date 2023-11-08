package com.pashonokk.marketplace.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategorySavingDto {
    @NotBlank(message = "Category`s name can`t be empty or null")
    private String name;
    private String description;
}
