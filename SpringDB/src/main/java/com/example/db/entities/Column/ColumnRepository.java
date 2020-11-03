package com.example.db.entities.Column;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnRepository extends JpaRepository<ColumnEntity, Integer> {
    @Query("select c from ColumnEntity c where c.table.name = :tableName")
    List<ColumnEntity> findByTableName(String tableName);

    List<ColumnEntity> findAllByTableId(Integer id);
}
