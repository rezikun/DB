package com.example.db.entities.Row;

import com.example.db.entities.Table.TableEntity;
import com.example.db.entities.Type.ValueType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="rows", schema = "public")
public class RowEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "row", fetch = FetchType.EAGER)
    private List<ValueType> values;

    @ManyToOne
    @JoinColumn(name="table_id", nullable = false)
    private TableEntity table;
}
