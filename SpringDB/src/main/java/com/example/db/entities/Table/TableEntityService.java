package com.example.db.entities.Table;

import com.example.db.entities.Database.DatabaseEntity;
import com.example.db.entities.Database.DatabaseRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TableEntityService {
    private final DatabaseRepository databaseRepository;
    private final TableRepository tableRepository;

    public TableEntityService(DatabaseRepository databaseRepository, TableRepository tableRepository) {
        this.databaseRepository = databaseRepository;
        this.tableRepository = tableRepository;
    }

    public TableEntity getOneByName(String name) {
        return tableRepository.findByName(name);
    }

    public TableEntity create(Integer databaseId, String name) {
        TableEntity table = new TableEntity();
        table.setName(name);
        if (this.databaseRepository.findById(databaseId).isEmpty()) {
            throw new RuntimeException("There is no such database");
        }
        table.setDatabase(this.databaseRepository.findById(databaseId).get());
        return this.tableRepository.save(table);
    }

    public TableEntity getOne(Integer id) {
        if (this.tableRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Provide valid id");
        }
        return this.tableRepository.findById(id).get();
    }

    public List<TableEntity> getAllByDB(Integer databaseId) {
        if (this.databaseRepository.findById(databaseId).isEmpty()) {
            throw new RuntimeException("There is no such database");
        }
        return this.tableRepository.findAllByDatabaseId(databaseId);
    }

    public TableEntity sortByColumn(Integer tableId, Integer columnId) {
        if (this.tableRepository.findById(tableId).isEmpty()) {
            throw new RuntimeException("Provide valid id");
        }
        TableEntity table = this.tableRepository.findById(tableId).get();
        var columns = table.getColumns();
        var columnNumber = IntStream.range(0, columns.size())
                .filter(i -> columns.get(i).getId().equals(columnId))
                .findFirst().getAsInt();
        System.out.println("INDEX " + columnNumber);
        table.getRows().sort(Comparator.comparing(o -> o.getValues().get(columnNumber)));
        return this.tableRepository.save(table);
    }
}
