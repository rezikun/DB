package com.example.db.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class LoadStorage {
    private static final Logger log = LoggerFactory.getLogger(LoadStorage.class);

    @Bean
    CommandLineRunner initStorage() {
        String storagePath = System.getProperty("user.dir") + "/storage";
        File db = new File(storagePath);
        boolean created = db.mkdir();
        return args -> {
            if (created) {
                log.info("Storage was initialized");
            }
        };
    }
}
