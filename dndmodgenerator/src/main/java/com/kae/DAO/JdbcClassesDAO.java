package com.kae.DAO;

import com.kae.Exceptions.DaoException;
import com.kae.Models.ClassModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcClassesDAO implements ClassesDAO {
    private final JdbcTemplate jdbc;

    public JdbcClassesDAO(DataSource datasource) {
        this.jdbc = new JdbcTemplate(datasource);
    }
    @Override
    public ClassModel getClassById(int id) {
        ClassModel classModel = null;
        String sql = "SELECT id, class_name\n" +
                "FROM classes\n" +
                "WHERE id = ?";
        try {
            SqlRowSet results = jdbc.queryForRowSet(sql, id);
            if (results.next()) {
                classModel = mapRowToClass(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return classModel;
    }

    @Override
    public List<ClassModel> getClasses() {
        List<ClassModel> classModels = new ArrayList<>();
        String sql = "SELECT id, class_name\n" +
                "FROM classes;";
        try {
            SqlRowSet results = jdbc.queryForRowSet(sql);
            while (results.next()) {
                classModels.add(mapRowToClass(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return classModels;
    }

    @Override
    public List<ClassModel> getClassesByCharacterId(int id) {
        List<ClassModel> classes = new ArrayList<>();
        String sql = "SELECT id, class_name\n" +
                "FROM classes\n" +
                "JOIN character_class AS cc ON cc.class_id = classes.id\n" +
                "WHERE cc.character_id = ?;";
        try {
            SqlRowSet results = jdbc.queryForRowSet(sql, id);
            while (results.next()) {
                classes.add(mapRowToClass(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return classes;
    }

    public ClassModel mapRowToClass(SqlRowSet results) {
        ClassModel classModel = new ClassModel();
        classModel.setId(results.getInt("id"));
        classModel.setName(results.getString("class_name"));
        return classModel;
    }
}
