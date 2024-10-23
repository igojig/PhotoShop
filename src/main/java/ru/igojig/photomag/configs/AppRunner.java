package ru.igojig.photomag.configs;

import com.dropbox.core.oauth.DbxRefreshResult;
import com.dropbox.core.v2.DbxClientV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final DbxClientV2 client;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        DbxRefreshResult dbxRefreshResult = client.refreshAccessToken();

        log.info("token refreshed");
        log.info(dbxRefreshResult.getAccessToken());
        log.info(dbxRefreshResult.getScope());
        log.info(String.valueOf(dbxRefreshResult.getExpiresAt()/1000/60));
        //code goes here

    }
}

