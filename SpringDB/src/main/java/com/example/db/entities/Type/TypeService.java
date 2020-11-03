package com.example.db.entities.Type;

import com.example.db.database.entities.types.Mapper;
import com.example.db.database.entities.types.TypeName;
import com.example.db.entities.Column.ColumnEntity;
import com.example.db.entities.Column.ColumnRepository;
import com.example.db.entities.Row.RowEntity;
import com.example.db.entities.Row.RowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TypeService {
    private final ValueTypeRepository valueTypeRepository;
    private final RowRepository rowRepository;
    private final ColumnRepository columnRepository;

    @Autowired
    public TypeService(ValueTypeRepository valueTypeRepository, RowRepository rowRepository, ColumnRepository columnRepository) {
        this.valueTypeRepository = valueTypeRepository;
        this.rowRepository = rowRepository;
        this.columnRepository = columnRepository;
    }

    public ValueType setData(Integer rowId, Integer columnId, String data) {
        if (this.columnRepository.findById(columnId).isEmpty()) {
            throw new RuntimeException("Provide valid column id");
        }
        if (this.rowRepository.findById(rowId).isEmpty()) {
            throw new RuntimeException("Provide valid row id");
        }
        ColumnEntity column = this.columnRepository.findById(columnId).get();
        RowEntity row = this.rowRepository.getOne(rowId);
        ValueType value = this.valueTypeRepository.findByColumnIdAndRowId(columnId, rowId);
        boolean valid = true;
        switch (column.getType()) {
            case INT:
                valid = isInteger(data);
                break;
            case REAL:
                valid = isReal(data);
                break;
            case INT_INTERVAL:
                valid = isInInterval(data, column);
                break;
            case CHAR:
                valid = isChar(data);
                break;
            case TEXT:
                valid = isPath(data);
                File file = new File(data);
                //Todo: save file
                value.setFile(file.getAbsolutePath());
                break;
        }
        if (!valid) {
            throw new RuntimeException("Value is not valid");
        }
        value.setData(data);
        value.setColumn(column);
        value.setRow(row);
        return this.valueTypeRepository.save(value);
    }

    public ValueType create(Integer rowId, Integer columnId) {
        if (this.columnRepository.findById(columnId).isEmpty()) {
            throw new RuntimeException("Provide valid column id");
        }
        if (this.rowRepository.findById(rowId).isEmpty()) {
            throw new RuntimeException("Provide valid row id");
        }
        ColumnEntity column = this.columnRepository.getOne(columnId);
        RowEntity row = this.rowRepository.getOne(rowId);
        ValueType value = new ValueType();
        value.setColumn(column);
        value.setRow(row);
        value.setData("");
        return this.valueTypeRepository.save(value);
    }

    private boolean isPath(String data) {
        Pattern pattern = Pattern.compile("/*(/*)+/?");
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }

    private boolean isInInterval(String data, ColumnEntity column) {
        if (!isInteger(data)) {
            return false;
        }
        Integer i = Integer.valueOf(data);
        return column.getMin() <= i && i <= column.getMax();
    }

    private boolean isInteger(String data) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }

    private boolean isReal(String data) {
        return true;
    }

    private boolean isChar(String data) {
        return data.length() == 1 || data.length() == 0 || data.isBlank();
    }
}
