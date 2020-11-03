package com.example.db.entities.Column;

import com.example.db.database.entities.types.TypeName;
import com.example.db.entities.Table.TableEntity;
import com.example.db.entities.Type.ValueType;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="columns", schema = "public")
public class ColumnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="column_name", nullable = false)
    private String name;

    @Column(name="type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeName type;

    @Nullable
    @Column(name="min_val", columnDefinition = "integer default 0")
    private Integer min;

    @Nullable
    @Column(name="max_val", columnDefinition = "integer default 0")
    private Integer max;

    @OneToMany(mappedBy = "column", fetch = FetchType.LAZY)
    private List<ValueType> values;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="table_id", nullable = false)
    private TableEntity table;
}
