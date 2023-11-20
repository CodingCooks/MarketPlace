package com.pashonokk.marketplace.controller;

import com.pashonokk.marketplace.dto.AdvertisementDto;
import com.pashonokk.marketplace.dto.AdvertisementSavingDto;
import com.pashonokk.marketplace.dto.AdvertisementUpdateDto;
import com.pashonokk.marketplace.endpoint.PageResponse;
import com.pashonokk.marketplace.exception.BigSizeException;
import com.pashonokk.marketplace.exception.EntityValidationException;
import com.pashonokk.marketplace.service.AdvertisementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/advertisements")
public class AdvertisementRestController {
    private final AdvertisementService advertisementService;
    private final Logger logger = LoggerFactory.getLogger(AdvertisementRestController.class);


    @GetMapping
    public ResponseEntity<PageResponse<AdvertisementDto>> getAdvertisements(@RequestParam(required = false, defaultValue = "0") int page,
                                                                            @RequestParam(required = false, defaultValue = "10") int size,
                                                                            @RequestParam(required = false, defaultValue = "id") String sort) {
        if (size > 100) {
            throw new BigSizeException("You can get maximum 100 advertisements at one time");
        }
        PageResponse<AdvertisementDto> allAdvertisements = advertisementService.getAllAdvertisements(PageRequest.of(page, size, Sort.by(sort)));
        return ResponseEntity.ok(allAdvertisements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDto> getAdvertisement(@PathVariable Long id) {
        return ResponseEntity.ok(advertisementService.getAdvertisement(id));
    }

    @PostMapping
    public ResponseEntity<AdvertisementDto> addAdvertisement(@RequestBody @Valid AdvertisementSavingDto advertisementSavingDto,
                                                             Errors errors,
                                                             @AuthenticationPrincipal UserDetails userDetails) {
        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(er -> logger.error(er.getDefaultMessage()));
            throw new EntityValidationException("Validation failed", errors);
        }
        AdvertisementDto savedAdvertisement = advertisementService.addAdvertisement(advertisementSavingDto, userDetails.getUsername());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAdvertisement.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedAdvertisement);
    }

    @PutMapping("/{advertisementId}")
    public ResponseEntity<AdvertisementDto> updateAdvertisement(@PathVariable Long advertisementId, @RequestBody @Valid AdvertisementUpdateDto advertisementUpdateDto, Errors errors) {
        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(er -> logger.error(er.getDefaultMessage()));
            throw new EntityValidationException("Validation failed", errors);
        }
        AdvertisementDto updatedAdvertisement = advertisementService
                .updateAdvertisement(advertisementUpdateDto, advertisementId);
        return ResponseEntity.ok(updatedAdvertisement);
    }

    @PatchMapping("/{advertisementId}")
    public ResponseEntity<AdvertisementDto> setAdvertisementAsNotActive(@PathVariable Long advertisementId) {
        AdvertisementDto advertisementDto = advertisementService.setAdvertisementAsNotActive(advertisementId);
        return ResponseEntity.ok(advertisementDto);
    }
}

