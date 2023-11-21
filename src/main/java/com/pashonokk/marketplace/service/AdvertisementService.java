package com.pashonokk.marketplace.service;

import com.pashonokk.marketplace.dto.AdvertisementDto;
import com.pashonokk.marketplace.dto.AdvertisementSavingDto;
import com.pashonokk.marketplace.dto.AdvertisementUpdateDto;
import com.pashonokk.marketplace.endpoint.PageResponse;
import com.pashonokk.marketplace.entity.Advertisement;
import com.pashonokk.marketplace.entity.Category;
import com.pashonokk.marketplace.entity.SubCategory;
import com.pashonokk.marketplace.entity.User;
import com.pashonokk.marketplace.mapper.AdvertisementMapper;
import com.pashonokk.marketplace.mapper.AdvertisementSavingMapper;
import com.pashonokk.marketplace.mapper.PageMapper;
import com.pashonokk.marketplace.repository.AdvertisementRepository;
import com.pashonokk.marketplace.repository.CategoryRepository;
import com.pashonokk.marketplace.repository.SubCategoryRepository;
import com.pashonokk.marketplace.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    private final PageMapper pageMapper;
    private final AdvertisementMapper advertisementMapper;
    private final AdvertisementSavingMapper advertisementSavingMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final SubCategoryRepository subCategoryRepository;
    private static final String ADVERTISEMENT_ERROR_MESSAGE = "Advertisement with id %s doesn't exist";
    private static final String CATEGORY_ERROR_MESSAGE = "Category with id %s doesn't exist";
    private static final String USER_ERROR_MESSAGE = "User with email %s doesn't exist";
    private static final String SUBCATEGORY_ERROR_MESSAGE = "SubCategory with id %s doesn't exist";

    @Transactional(readOnly = true)
    public PageResponse<AdvertisementDto> getAllAdvertisements(Pageable pageable) {
        return pageMapper.toPageResponse(advertisementRepository
                .findAll(pageable)
                .map(advertisementMapper::toDto));
    }

    @Transactional(readOnly = true)
    public AdvertisementDto getAdvertisement(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ADVERTISEMENT_ERROR_MESSAGE, id)));
        return advertisementMapper.toDto(advertisement);
    }

    @Transactional
    public AdvertisementDto addAdvertisement(AdvertisementSavingDto advertisementSavingDto, String userEmail) {
        Advertisement advertisement = advertisementSavingMapper.toEntity(advertisementSavingDto);
        Category category = categoryRepository.findById(advertisementSavingDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(CATEGORY_ERROR_MESSAGE,
                        advertisementSavingDto.getCategoryId())));

        SubCategory subCategory = subCategoryRepository.findById(advertisementSavingDto.getSubCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(SUBCATEGORY_ERROR_MESSAGE,
                        advertisementSavingDto.getSubCategoryId())));

        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_ERROR_MESSAGE,
                        userEmail)));
        advertisement.setCategory(category);
        advertisement.setSubCategory(subCategory);
        advertisement.linkImages();
        advertisement.setUser(user);
        advertisement.setCreationDate(LocalDate.now());
        Advertisement savedAdvertisement = advertisementRepository.save(advertisement);
        return advertisementMapper.toDto(savedAdvertisement);
    }

    @Transactional
    public AdvertisementDto updateAdvertisement(AdvertisementUpdateDto advertisementUpdateDto,
                                                Long advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ADVERTISEMENT_ERROR_MESSAGE, advertisementId)));

        Optional.ofNullable(advertisementUpdateDto.getName()).ifPresent(advertisement::setName);
        Optional.ofNullable(advertisementUpdateDto.getDescription()).ifPresent(advertisement::setDescription);
        Optional.ofNullable(advertisementUpdateDto.getLocation()).ifPresent(advertisement::setLocation);
        return advertisementMapper.toDto(advertisement);
    }

    @Transactional
    public AdvertisementDto setAdvertisementAsNotActive(Long advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ADVERTISEMENT_ERROR_MESSAGE, advertisementId)));
        advertisement.setActive(false);
        return advertisementMapper.toDto(advertisement);
    }
}
