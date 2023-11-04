package com.pashonokk.marketplace.service;


import com.pashonokk.marketplace.dto.AuthorizationToken;
import com.pashonokk.marketplace.dto.JwtAuthorizationResponse;
import com.pashonokk.marketplace.dto.UserAuthorizationDto;
import com.pashonokk.marketplace.dto.UserSavingDto;
import com.pashonokk.marketplace.entity.Phone;
import com.pashonokk.marketplace.entity.Role;
import com.pashonokk.marketplace.entity.User;
import com.pashonokk.marketplace.exception.AuthenticationException;
import com.pashonokk.marketplace.exception.UserExistsException;
import com.pashonokk.marketplace.mapper.UserSavingMapper;
import com.pashonokk.marketplace.repository.RoleRepository;
import com.pashonokk.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserSavingMapper userSavingMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final String USER_EXISTS_ERROR_MESSAGE = "User with email %s already exists";


    public void saveRegisteredUser(UserSavingDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserExistsException(String.format(USER_EXISTS_ERROR_MESSAGE, userDto.getEmail()));
        }
        User user = userSavingMapper.toEntity(userDto);
        Phone phone = new Phone(userDto.getPhone());
        phone.setUser(user);
        user.getPhones().add(phone);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role roleUser = roleRepository.findRoleByName("ROLE_USER");
        user.setRole(roleUser);
        userRepository.save(user);
    }

    public JwtAuthorizationResponse authorize(UserAuthorizationDto userDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Email or password is wrong, try again");
        }
        User user = userRepository.findUserByEmail(userDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + userDto.getEmail() + " doesn`t exist"));
        String token = jwtService.generateToken(user);
        OffsetDateTime expiresAt = jwtService.getExpiration(token);
        List<String> permissions = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return new JwtAuthorizationResponse(new AuthorizationToken(token, expiresAt), user.getRole().getName(), permissions);
    }

}
