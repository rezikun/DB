package com.example.db.entities.Database;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseMutation implements GraphQLMutationResolver {
    private final DBEntityService service;

    @Autowired
    public DatabaseMutation(DBEntityService service) {
        this.service = service;
    }

    public DatabaseEntity createDatabase(String name) {
        return this.service.createDB(name);
    }

    public Boolean deleteDatabase(Integer id) {
        this.service.delete(id);
        return true;
    }
}
