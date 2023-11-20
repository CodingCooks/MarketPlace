package com.pashonokk.marketplace.controller;

import com.pashonokk.marketplace.dto.*;
import com.pashonokk.marketplace.exception.EntityValidationException;
import com.pashonokk.marketplace.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String VALIDATION_EXCEPTION_MESSAGE = "Validation failed";

    @GetMapping("{userId}/active/advertisements")
    public ResponseEntity<List<AdvertisementDto>> getActiveUserAdvertisements(@PathVariable Long userId) {
        List<AdvertisementDto> allActiveAdvertisements = userService.getActiveUserAdvertisements(userId);
        return ResponseEntity.ok(allActiveAdvertisements);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserSavingDto userDto, Errors errors) {
        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(er -> logger.error(er.getDefaultMessage()));
            throw new EntityValidationException(VALIDATION_EXCEPTION_MESSAGE, errors);
        }
        userService.saveRegisteredUser(userDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/authorize")
    public ResponseEntity<JwtAuthorizationResponse> authorize(@RequestBody @Valid UserAuthorizationDto userDto, Errors errors) {
        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(er -> logger.error(er.getDefaultMessage()));
            throw new EntityValidationException(VALIDATION_EXCEPTION_MESSAGE, errors);
        }
        return ResponseEntity.ok(userService.authorize(userDto));
    }

    @PostMapping("/change-password/{encryptedEmail}")
    public ResponseEntity<Void> changePassword(@PathVariable("encryptedEmail") String encryptedEmail,
                                               @RequestBody @Valid PasswordChangingDto passwordChangingDto,
                                               Errors errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(er -> logger.error(er.getDefaultMessage()));
            throw new EntityValidationException(VALIDATION_EXCEPTION_MESSAGE, errors);
        }
        userService.changePassword(passwordChangingDto, encryptedEmail);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserAccount(@PathVariable("userId") Long userId) {
        userService.deleteUserAccount(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/add-liked-advertisement/{advertisementId}")
    public ResponseEntity<Void> addLikedAdvertisement(@PathVariable Long advertisementId,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        userService.addLikedAdvertisement(advertisementId, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}
