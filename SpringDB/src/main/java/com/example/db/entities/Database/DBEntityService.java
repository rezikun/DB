package com.example.db.entities.Database;

import com.example.db.dto.database.DBCreationDto;
import com.example.db.dto.database.DBDto;
import com.example.db.dto.table.TableCreationDto;
import com.example.db.dto.table.TableDto;
import com.example.db.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DBEntityService {
    private final DatabaseRepository databaseRepository;

    @Autowired
    public DBEntityService(DatabaseRepository databaseRepository) {

        this.databaseRepository = databaseRepository;
    }

    @Transactional(readOnly = true)
    public DatabaseEntity getOne(int id) {
        return this.databaseRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<DatabaseEntity> getAllDB() {
        return this.databaseRepository.findAll();
    }

    public DatabaseEntity createDB(String name) {
        DatabaseEntity db = new DatabaseEntity();
        db.setName(name);
        return this.databaseRepository.save(db);
    }

//    public List<DBDto> getAll() {
//        return DBDto.fromEntities(this.databaseRepository.findAll());
//    }

//    public DBDto getOneByName(String name) {
//        return DBDto.fromEntity(this.databaseRepository.findByName(name));
//    }

//    public DBDto create(DBCreationDto dto) {
//        DatabaseEntity db = new DatabaseEntity();
//        db.setName(dto.getName());
//        if (!dto.getTables().isEmpty()) {
//            db.setTables(TableCreationDto.toEntities(dto.getTables(), db));
//        }
//        databaseRepository.save(db);
//        return DBDto.fromEntity(db);
//    }

    public void deleteByName(String name) {
        databaseRepository.deleteByName(name);
    }

    public void delete(Integer id) {
        this.databaseRepository.deleteById(id);
    }
}
