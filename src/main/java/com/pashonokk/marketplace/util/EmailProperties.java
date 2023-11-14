package com.pashonokk.marketplace.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "mail.link.to")
@Configuration
@Data
public class EmailProperties {
    private String changePassword;
    private String confirmEmail;
}
