package com.example.db.entities.Type;

import com.example.db.entities.Column.ColumnEntity;
import com.example.db.entities.Row.RowEntity;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.File;

@Data
@Entity
@Table(name="values", schema = "public")
public class ValueType implements Comparable<ValueType>{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="data")
    private String data;

    @Column(name="file")
    @Lob
    private String file;

    @ManyToOne
    @JoinColumn(name="row_id", nullable = false)
    private RowEntity row;

    @ManyToOne
    @JoinColumn(name="column_id", nullable = false)
    private ColumnEntity column;

    @Override
    public int compareTo(@NotNull ValueType o) {
        switch (this.column.getType()) {
            case STRING:
            case CHAR:
                return this.data.compareTo(o.getData());
            case INT:
            case INT_INTERVAL:
                return Integer.valueOf(this.data).compareTo(Integer.valueOf(o.getData()));
            case REAL:
                return Double.valueOf(this.data).compareTo(Double.valueOf(o.getData()));
            case TEXT:
                File file1 = new File(this.data);
                File file2 = new File(o.getData());
                return Long.compare(file1.getTotalSpace(), file2.getTotalSpace());
            default:
                throw new RuntimeException("No such type");
        }
    }
}
