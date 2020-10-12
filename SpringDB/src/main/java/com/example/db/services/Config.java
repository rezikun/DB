package com.example.db.services;

import com.example.db.entities.DataBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = DBService.class)
public class Config {
    @Bean
    public DataBase getDataBase() {
        return new DataBase();
    }
}
