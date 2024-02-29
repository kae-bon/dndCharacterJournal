package com.kae.DAO;

import com.kae.Exceptions.DaoException;
import com.kae.Models.ClassModel;
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
        ClassModel classModel = new ClassModel();
        String sql = "SELECT id, class_name\n" +
                "FROM classes\n" +
                "WHERE id = ?";
        try {
            SqlRowSet results = jdbc.queryForRowSet(sql, id);
            if (results.next()) {
                classModel = mapRowToPcClass(results);
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
                classModels.add(mapRowToPcClass(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return classModels;
    }

    public ClassModel mapRowToPcClass(SqlRowSet results) {
        ClassModel classModel = new ClassModel();
        classModel.setId(results.getInt("id"));
        classModel.setName(results.getString("class_name"));
        return classModel;
    }
}
