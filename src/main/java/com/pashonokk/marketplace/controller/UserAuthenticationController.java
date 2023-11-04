package com.pashonokk.marketplace.controller;


import com.pashonokk.marketplace.dto.JwtAuthorizationResponse;
import com.pashonokk.marketplace.dto.UserAuthorizationDto;
import com.pashonokk.marketplace.exception.EntityValidationException;
import com.pashonokk.marketplace.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserAuthenticationController {
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);

    @PostMapping("/users/authorize")
    public ResponseEntity<JwtAuthorizationResponse> authorize(@RequestBody @Valid UserAuthorizationDto userDto, Errors errors) {
        if(errors.hasErrors()){
            errors.getFieldErrors().forEach(er->logger.error(er.getDefaultMessage()));
            throw new EntityValidationException("Validation failed", errors);
        }
        return ResponseEntity.ok(userService.authorize(userDto));
    }
}
