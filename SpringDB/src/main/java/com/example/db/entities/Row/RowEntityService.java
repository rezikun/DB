package com.example.db.entities.Row;

import com.example.db.entities.Column.ColumnRepository;
import com.example.db.entities.Table.TableEntity;
import com.example.db.entities.Table.TableRepository;
import com.example.db.entities.Type.Base.BaseTypeRepository;
import com.example.db.entities.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RowService {
    private final RowRepository rowRepository;
    private final ColumnRepository columnRepository;
    private final TableRepository tableRepository;

    private final TypeService typeService;

    @Autowired
    public RowService(RowRepository rowRepository,
                      ColumnRepository columnRepository,
                      TableRepository tableRepository,
                      TypeService typeService) {
        this.rowRepository = rowRepository;
        this.columnRepository = columnRepository;
        this.tableRepository = tableRepository;
        this.typeService = typeService;
    }

    @Transactional
    public Row create(Integer tableId) {
        if (this.tableRepository.findById(tableId).isEmpty()) {
            throw new RuntimeException("Couldn`t create column without table id");
        }
        var columns = this.columnRepository.findAllByTableId(tableId);
        Row row = new Row();
        columns.forEach(c -> {
            this.typeService.create(row.getId(), c.getId(), "");
        });
        row.setTable(this.tableRepository.getOne(tableId));
        return this.rowRepository.save(row);
    }
}
