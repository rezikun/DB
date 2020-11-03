package com.example.db.entities.Row;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RowEntityQuery implements GraphQLQueryResolver {
    private final RowEntityService service;

    public RowEntityQuery(RowEntityService service) {
        this.service = service;
    }

    public List<RowEntity> getRows(Integer tableId, Integer count) {
        return this.service.getAllByTable(tableId).stream().limit(count)
                .collect(Collectors.toList());
    }
}
