package com.example.db.entities.Table;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.db.entities.Column.ColumnEntity;
import com.example.db.entities.Column.ColumnEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TableEntityMutation implements GraphQLMutationResolver {
    @Autowired
    private TableEntityService service;

    public TableEntity createTable(Integer databaseId, String name) {
        return service.create(databaseId, name);
    }

    public TableEntity sort(Integer tableId, Integer columnId) { return service.sortByColumn(tableId, columnId);}
}
