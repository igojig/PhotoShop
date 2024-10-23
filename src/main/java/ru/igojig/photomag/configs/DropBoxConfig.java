package ru.igojig.photomag.configs;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.v2.DbxClientV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DropBoxConfig {

    private final DropBoxProperties dropBoxProperties;

    @Bean
    public DbxClientV2 dbxClientV2(){
        DbxRequestConfig config = DbxRequestConfig.newBuilder("JMS-Shop")
                .build();

        final DbxCredential credential = new DbxCredential(
                "",
                0L,
                dropBoxProperties.getRefreshToken(),
                dropBoxProperties.getAppKey(),
                dropBoxProperties.getAppSecret());

        DbxClientV2 client = new DbxClientV2(config, credential);

        return client;
    }
}
