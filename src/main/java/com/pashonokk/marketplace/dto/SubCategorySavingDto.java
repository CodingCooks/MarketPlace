package com.pashonokk.marketplace.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubCategorySavingDto {
    @NotBlank(message = "Category`s name can`t be empty or null")
    private String name;
    private String description;
    @NotNull(message = "Category id can`t be empty or null")
    @Min(value = 0, message = "Id can`t be negative")
    private Long categoryId;
}
