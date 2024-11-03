package ru.igojig.photomag.configs;

import io.awspring.cloud.core.region.StaticRegionProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
@RequiredArgsConstructor
public class S3Config {

    private final S3Properties s3Properties;

    @Bean
    public S3Client s3Client() {

        return S3Client.builder()
                .credentialsProvider(customAwsCredentialsProvider())
                .endpointOverride(URI.create(s3Properties.getUrl()))
                .region(Region.of(s3Properties.getRegion()))
                .build();
    }

    @Bean
    public AwsCredentialsProvider customAwsCredentialsProvider() {
        return StaticCredentialsProvider.create(awsCredentials());
    }

    @Bean
    public AwsCredentials awsCredentials(){
        return AwsBasicCredentials.create(s3Properties.getAws_access_key_id(),
                s3Properties.getAws_secret_access_key());
    }

    @Bean
    public AwsRegionProvider customRegionProvider() {
        return new StaticRegionProvider(s3Properties.getRegion());
    }
}
