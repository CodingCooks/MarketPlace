package com.pashonokk.marketplace.service;

import com.pashonokk.marketplace.entity.Token;
import com.pashonokk.marketplace.entity.User;
import com.pashonokk.marketplace.exception.TokenExpiredException;
import com.pashonokk.marketplace.repository.TokenRepository;
import com.pashonokk.marketplace.util.TokenProperties;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final TokenProperties tokenProperties;

    @Transactional(readOnly = true)
    public User validateToken(String value) {
        Token tokenByValue = tokenRepository.getTokenByValue(value)
                .orElseThrow(() -> new EntityNotFoundException("There isn`t user with such token " + value));
        OffsetDateTime offsetDateTime = tokenByValue.getCreateTime().plusSeconds(tokenProperties.getDuration().getSeconds());
        if (offsetDateTime.isBefore(OffsetDateTime.now())) {
            throw new TokenExpiredException("Token " + value + " is no longer valid");
        }
        return tokenByValue.getUser();
    }
}
