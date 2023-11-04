package com.pashonokk.marketplace.controller;

import com.pashonokk.marketplace.dto.PhoneDto;
import com.pashonokk.marketplace.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/phones")
public class PhoneRestController {
    private final PhoneService phoneService;

    @GetMapping("/{id}")
    public ResponseEntity<PhoneDto> getPhoneById(@PathVariable Long id) {
        PhoneDto phone = phoneService.getPhoneById(id);
        return ResponseEntity.ok(phone);
    }

    @PutMapping("/{userId}/newMain/{newId}")
    public ResponseEntity<PhoneDto> setNewPhoneAsMain(@PathVariable Long userId, @PathVariable Long newId) {
        PhoneDto newMainPhone = phoneService.setNewPhoneAsMain(userId, newId);
        return ResponseEntity.ok(newMainPhone);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.pashonokk.marketplace.enumeration.Permissions).DELETE_ACCESS)")
    public ResponseEntity<Object> deletePhone(@PathVariable Long id) {
        phoneService.deletePhone(id);
        return ResponseEntity.noContent().build();
    }
}
