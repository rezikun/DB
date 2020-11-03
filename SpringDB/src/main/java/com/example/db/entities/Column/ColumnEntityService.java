package com.example.db.entities.Column;

import com.example.db.database.entities.types.Mapper;
import com.example.db.database.entities.types.TypeName;
import com.example.db.entities.Table.TableEntity;
import com.example.db.entities.Table.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.util.List;

@Service
public class ColumnEntityService {
    private final ColumnRepository columnRepository;
    private final TableRepository tableRepository;

    @Autowired
    public ColumnEntityService(ColumnRepository columnRepository, TableRepository tableRepository) {
        this.columnRepository = columnRepository;
        this.tableRepository = tableRepository;
    }

    @Transactional
    public List<ColumnEntity> getByTable(String tableName) {
        return columnRepository.findByTableName(tableName);
    }

    public ColumnEntity create(String name, String type, Integer tableId) {
        ColumnEntity column = new ColumnEntity();
        column.setName(name);
        column.setType(Mapper.toType(type));
        if (this.tableRepository.findById(tableId).isEmpty()) {
            throw new RuntimeException("Couldn`t create column without table id");
        }
        column.setTable(this.tableRepository.findById(tableId).get());
        return this.columnRepository.save(column);
    }

    public ColumnEntity create(String name, String type, Integer tableId, Integer min, Integer max) {
        ColumnEntity column = new ColumnEntity();
        column.setName(name);
        column.setType(Mapper.toType(type));
        if (this.tableRepository.findById(tableId).isEmpty()) {
            throw new RuntimeException("Couldn`t create column without table id");
        }
        if (Mapper.toType(type).equals(TypeName.INT_INTERVAL) && min <= max) {
            column.setMin(min);
            column.setMax(max);
        }
        column.setTable(this.tableRepository.findById(tableId).get());
        return this.columnRepository.save(column);
    }

    public List<ColumnEntity> getByTableId(Integer tableId) {
        if (this.tableRepository.findById(tableId).isEmpty()) {
            throw new RuntimeException("Provide valid table id");
        }
        return this.columnRepository.findAllByTableId(tableId);
    }
}
