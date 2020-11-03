package com.example.db.entities.Column;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ColumnEntityMutation implements GraphQLMutationResolver {
    @Autowired
    private ColumnEntityService service;

    public ColumnEntity createColumn(String name, String type, Integer tableId) {
        return service.create(name, type, tableId);
    }

    public ColumnEntity createColumn(String name, String type, Integer tableId, Integer min, Integer max) {
        return service.create(name, type, tableId, min, max);
    }
}
