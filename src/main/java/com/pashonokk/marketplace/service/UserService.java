package com.pashonokk.marketplace.service;


import com.pashonokk.marketplace.dto.*;
import com.pashonokk.marketplace.entity.Advertisement;
import com.pashonokk.marketplace.entity.Role;
import com.pashonokk.marketplace.entity.Token;
import com.pashonokk.marketplace.entity.User;
import com.pashonokk.marketplace.event.UserRegistrationCompletedEvent;
import com.pashonokk.marketplace.exception.*;
import com.pashonokk.marketplace.mapper.AddressSavingMapper;
import com.pashonokk.marketplace.mapper.AdvertisementMapper;
import com.pashonokk.marketplace.mapper.UserMapper;
import com.pashonokk.marketplace.mapper.UserSavingMapper;
import com.pashonokk.marketplace.repository.AdvertisementRepository;
import com.pashonokk.marketplace.repository.RoleRepository;
import com.pashonokk.marketplace.repository.UserRepository;
import com.pashonokk.marketplace.util.EmailProperties;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final RoleRepository roleRepository;
    private final UserSavingMapper userSavingMapper;
    private final UserMapper userMapper;
    private final AdvertisementMapper advertisementMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final EmailProperties emailProperties;
    private final StringEncryptor stringEncryptor;
    @Value("${spring.mail.username}")
    private String emailFrom;
    private final AddressSavingMapper addressSavingMapper;
    private static final String USER_EXISTS_ERROR_MESSAGE = "User with email %s already exists";
    private static final String USER_DOESNT_EXISTS_ERROR_MESSAGE = "User with email %s doesn't exists";
    private static final String USER_WITH_ID_DOESNT_EXISTS_ERROR_MESSAGE = "User with id %s doesn't exists";
    private static final String ADVERTISEMENT_ERROR_MESSAGE = "Advertisement with id %s doesn't exist";



    @Transactional
    public void saveRegisteredUser(UserSavingDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserExistsException(String.format(USER_EXISTS_ERROR_MESSAGE, userDto.getEmail()));
        }
        User user = userSavingMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role roleUser = roleRepository.findRoleByName("ROLE_USER");
        user.setRole(roleUser);
        Token token = new Token();
        token.addUser(user);
        userRepository.save(user);
        SimpleMailMessage mailMessage = createMailMessage(user.getEmail(),
                emailProperties.getConfirmEmail() + token.getValue(),
                "Follow this link to confirm your email");
        applicationEventPublisher.publishEvent(new UserRegistrationCompletedEvent(mailMessage));
    }

    private SimpleMailMessage createMailMessage(String userEmail, String text, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(userEmail);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }

    @Transactional
    public JwtAuthorizationResponse authorize(UserAuthorizationDto userDto) {
        User user = userRepository.findUserByEmail(userDto.getEmail())
                .orElseThrow(() -> new UserDoesntExistException(
                        String.format(USER_DOESNT_EXISTS_ERROR_MESSAGE, userDto.getEmail())));
        userChecks(userDto, user);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Password is wrong, try again");
        }
        String token = jwtService.generateToken(user);
        OffsetDateTime expiresAt = jwtService.getExpiration(token);
        List<String> permissions = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return new JwtAuthorizationResponse(new AuthorizationToken(token, expiresAt), user.getRole().getName(), permissions);
    }

    private static void userChecks(UserAuthorizationDto userDto, User user) {
        if (!user.isEnabled()) {
            throw new UserIsNotVerifiedException("User email " + userDto.getEmail() + " is not verified");
        }
        if (user.getIsDeleted()) {
            throw new UserDeletedException("User email " + userDto.getEmail() + " was deleted");
        }
    }

    @SneakyThrows
    @Transactional
    public void changePassword(PasswordChangingDto passwordChangingDto, String encryptedEmail) {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedEmail);
        String email = stringEncryptor.decrypt(new String(decodedBytes, StandardCharsets.UTF_8));
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_DOESNT_EXISTS_ERROR_MESSAGE, encryptedEmail)));

        user.setPassword(passwordEncoder.encode(passwordChangingDto.getNewPassword()));
    }

    @SneakyThrows
    public void sendLinkToChangePassword(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new UsernameNotFoundException(
                    String.format(USER_DOESNT_EXISTS_ERROR_MESSAGE, email));
        }
        String encodedEmailForUrl = Base64.getEncoder().encodeToString(stringEncryptor.encrypt(email).getBytes(StandardCharsets.UTF_8));
        SimpleMailMessage mailMessage = createMailMessage(email,
                emailProperties.getChangePassword() + encodedEmailForUrl,
                "Follow this link to change your password");
        applicationEventPublisher.publishEvent(new UserRegistrationCompletedEvent(mailMessage));
    }

    @Transactional
    public void deleteUserAccount(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UsernameNotFoundException(
                    String.format(USER_WITH_ID_DOESNT_EXISTS_ERROR_MESSAGE, userId));
        }
        userRepository.setUserAsDeleted(userId);
    }

    @Transactional(readOnly = true)
    public List<AdvertisementDto> getActiveUserAdvertisements(Long userId) {
        List<Advertisement> allActiveAdvertisementsById = advertisementRepository.findAllActiveAdvertisementsById(userId);
        return allActiveAdvertisementsById.stream().map(advertisementMapper::toDto).toList();
    }

    @Transactional
    public void addLikedAdvertisement(Long advertisementId, String userEmail) {
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(USER_DOESNT_EXISTS_ERROR_MESSAGE, userEmail)));
        Advertisement advertisement = advertisementRepository
                .findWithPessimisticWriteLockById(advertisementId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ADVERTISEMENT_ERROR_MESSAGE, advertisementId)));
        advertisement.setLikes(advertisement.getLikes() + 1);
        user.getSavedAdvertisements().add(advertisement);
    }

    @Transactional(readOnly = true)
    public List<AdvertisementDto> getLikedUserAdvertisements(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(USER_WITH_ID_DOESNT_EXISTS_ERROR_MESSAGE, userId)));
        return user.getSavedAdvertisements().stream().map(advertisementMapper::toDto).toList();
    }

    @Transactional
    public UserDto editUser(Long userId, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(USER_WITH_ID_DOESNT_EXISTS_ERROR_MESSAGE, userId)));

        Optional.ofNullable(userUpdateDto.getUsername()).ifPresent(user::setUsername);
        Optional.ofNullable(userUpdateDto.getPhotoUrl()).ifPresent(user::setPhotoUrl);
        Optional.ofNullable(userUpdateDto.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(userUpdateDto.getLastName()).ifPresent(user::setLastName);
        Optional.ofNullable(userUpdateDto.getPhoneNumber()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(userUpdateDto.getIsAddressForAllAdvertisements()).ifPresent(user::setAddressForAllAdvertisements);
        return userMapper.toDto(user);
    }

    @Transactional
    public void setAddressForUser(AddressSavingDto addressSavingDto, String userEmail) {
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(USER_DOESNT_EXISTS_ERROR_MESSAGE, userEmail)));
        user.setAddress(addressSavingMapper.toEntity(addressSavingDto));
    }
}
