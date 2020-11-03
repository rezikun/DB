package com.example.db.entities.Database;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabaseQuery implements GraphQLQueryResolver {
    private final DBEntityService service;

    @Autowired
    public DatabaseQuery(DBEntityService service) {
        this.service = service;
    }

    public DatabaseEntity getDatabase(int id) {
        return service.getOne(id);
    }

    public List<DatabaseEntity> getDatabases(int count) {
        return service.getAllDB()
                .stream()
                .limit(count)
                .collect(Collectors.toList());
    }
}
