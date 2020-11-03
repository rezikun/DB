package com.example.db.entities.Table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Integer> {
    TableEntity findByName(String name);

    List<TableEntity> findAllByDatabaseId(Integer databaseId);
}
