package com.pashonokk.marketplace.controller;

import com.pashonokk.marketplace.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/advertisements")
public class AdvertisementRestController {
    private final AdvertisementService advertisementService;
    //todo get by id
    //post
    //edit
    //delete
    //get all
    //set as not active
}

