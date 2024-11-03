package ru.igojig.photomag.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:yandex.yaml")
@ConfigurationProperties
@Data
public class S3Properties {
    private String aws_access_key_id;
    private String aws_secret_access_key;
    private String region;
    private String url;
    private String bucket;
}
