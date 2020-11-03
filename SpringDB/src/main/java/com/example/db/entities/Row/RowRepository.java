package com.example.db.entities.Row;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RowRepository extends JpaRepository<RowEntity, Integer> {
    List<RowEntity> findAllByTableId(Integer id);
}
