package ru.igojig.photomag.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties
@PropertySource("classpath:dropbox.yaml")
@Data
public class DropBoxProperties {
    //    private String secret;
    private String refreshToken;
    private String appKey;
    private String appSecret;
}

