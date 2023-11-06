package com.pashonokk.marketplace.dto;

import com.pashonokk.marketplace.annotation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class PasswordChangingDto {
    @Email(message = "You entered wrong email")
    private String email;

    @NotBlank(message = "Password can`t be empty or null")
    @Size(min = 10, message = "Password must have more than 10 characters")
    private String newPassword;

    @NotBlank(message = "Password can`t be empty or null")
    @Size(min = 10, message = "Password must have more than 10 characters")
    private String newPasswordConfirmation;
}
