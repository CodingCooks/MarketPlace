package com.pashonokk.marketplace.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSavingDto {
    @Email(message = "You entered wrong email")
    private String email;
    @NotBlank(message = "Password can`t be empty or null")
    @Size(min = 10, message = "Password must have more than 10 characters")
    private String password;
    @NotBlank(message = "First name can`t be empty or null")
    private String firstName;
    @NotBlank(message = "Last name can`t be empty or null")
    private String lastName;
    @NotBlank(message = "Username can`t be empty or null")
    private String username;
    @NotBlank(message = "Phone can`t be empty or null")
    @Size(min = 10, max = 10, message = "Phone number must have 10 digits")
    private String phone;
}
