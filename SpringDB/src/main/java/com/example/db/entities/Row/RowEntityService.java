package com.example.db.entities.Row;

import com.example.db.entities.Column.ColumnRepository;
import com.example.db.entities.Table.TableRepository;
import com.example.db.entities.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class RowEntityService {
    private final RowRepository rowRepository;
    private final ColumnRepository columnRepository;
    private final TableRepository tableRepository;

    private final TypeService typeService;

    @Autowired
    public RowEntityService(RowRepository rowRepository,
                            ColumnRepository columnRepository,
                            TableRepository tableRepository,
                            TypeService typeService) {
        this.rowRepository = rowRepository;
        this.columnRepository = columnRepository;
        this.tableRepository = tableRepository;
        this.typeService = typeService;
    }

    public RowEntity create(Integer tableId) {
        if (this.tableRepository.findById(tableId).isEmpty()) {
            throw new RuntimeException("Provide valid id");
        }
        var columns = this.columnRepository.findAllByTableId(tableId);
        RowEntity row = new RowEntity();
        row.setTable(this.tableRepository.getOne(tableId));
        this.rowRepository.save(row);

        columns.forEach(c -> {
            this.typeService.create(row.getId(), c.getId());
        });

        return row;
    }

    public List<RowEntity> getAllByTable(Integer tableId) {
        return this.rowRepository.findAllByTableId(tableId);
    }
}
