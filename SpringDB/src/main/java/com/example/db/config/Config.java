package com.example.db.config;

import com.example.db.database.entities.DataBase;
import com.example.db.database.services.MainDBService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = MainDBService.class)
public class Config {
    @Bean
    public DataBase getDataBase() {
        return new DataBase();
    }
}
