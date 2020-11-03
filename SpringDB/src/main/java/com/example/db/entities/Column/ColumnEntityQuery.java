package com.example.db.entities.Column;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ColumnEntityQuery implements GraphQLQueryResolver {
    @Autowired
    private ColumnEntityService service;

    public List<ColumnEntity> getColumns(Integer tableId) {
        return service.getByTableId(tableId);
    }
}
