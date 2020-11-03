package com.example.db.entities.Table;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.db.entities.Database.DBEntityService;
import com.example.db.entities.Database.DatabaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TableEntityQuery implements GraphQLQueryResolver {
    private final TableEntityService service;

    @Autowired
    public TableEntityQuery(TableEntityService service) {
        this.service = service;
    }

    public TableEntity getTable(Integer id) {
        return service.getOne(id);
    }

    public List<TableEntity> getTables(Integer databaseId) {
        return service.getAllByDB(databaseId);
    }
}
