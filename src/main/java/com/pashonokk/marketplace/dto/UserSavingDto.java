package com.pashonokk.marketplace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    private String firstName;
    private String lastName;
    @NotBlank(message = "Username can`t be empty or null")
    private String username;
    @NotBlank(message = "Phone number can`t be empty or null")
    private String phoneNumber;
}
