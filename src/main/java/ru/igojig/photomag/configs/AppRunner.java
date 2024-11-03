package ru.igojig.photomag.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {



    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

//        DbxRefreshResult dbxRefreshResult = client.refreshAccessToken();
//
//        log.info("token refreshed");
//        log.info(dbxRefreshResult.getAccessToken());
//        log.info(dbxRefreshResult.getScope());
//        log.info(String.valueOf(dbxRefreshResult.getExpiresAt()/1000/60));
        //code goes here

    }
}

