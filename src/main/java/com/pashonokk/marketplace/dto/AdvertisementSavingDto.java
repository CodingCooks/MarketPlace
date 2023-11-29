package com.pashonokk.marketplace.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementSavingDto {
    @NotBlank(message = "Advertisement name cannot be null or empty")
    private String name;
    @NotBlank(message = "Advertisement description cannot be null or empty")
    private String description;
    @NotNull(message = "Category id can`t be null")
    @Min(value = 0, message = "Category id can`t be negative")
    private Long categoryId;
    @NotNull(message = "SubCategory id can`t be null")
    @Min(value = 0, message = "SubCategory id can`t be negative")
    private Long subCategoryId;
    @NotNull
    private Set<@Valid ImageSavingDto> images;
    private String location;
    private Long likes = 0L;
    private Long views = 0L;
}
