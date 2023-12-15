package com.pashonokk.marketplace.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String photoUrl;
    private Boolean isAddressForAllAdvertisements;
}
