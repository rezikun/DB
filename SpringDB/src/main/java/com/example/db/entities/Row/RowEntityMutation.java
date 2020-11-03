package com.example.db.entities.Row;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RowEntityMutation implements GraphQLMutationResolver {
    private final RowEntityService service;

    @Autowired
    public RowEntityMutation(RowEntityService service) {
        this.service = service;
    }

    public RowEntity addRow(Integer tableId) {
        return this.service.create(tableId);
    }
}
