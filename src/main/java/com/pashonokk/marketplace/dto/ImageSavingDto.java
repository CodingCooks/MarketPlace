package com.pashonokk.marketplace.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ImageSavingDto {
    @NotBlank(message = "Image url cannot be null or empty")
    private String url;
    private boolean main;
}
