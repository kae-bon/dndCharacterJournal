package com.kae.DAO;

import com.kae.Exceptions.DaoException;
import com.kae.Models.PcClass;
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
    public PcClass getClassById(int id) {
        PcClass pcClass = null;
        String sql = "SELECT id, class_name\n" +
                "FROM classes\n" +
                "WHERE id = ?";
        try {
            SqlRowSet results = jdbc.queryForRowSet(sql, id);
            if (results.next()) {
                pcClass = mapRowToClass(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return pcClass;
    }

    public PcClass getClassByName(String name) {
        PcClass pcClass = null;
        String sql = "SELECT id, class_name\n" +
                "FROM classes\n" +
                "WHERE class_name ILIKE ?";
        try {
            SqlRowSet results = jdbc.queryForRowSet(sql, name);
            if (results.next()) {
                pcClass = mapRowToClass(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return pcClass;
    }

    @Override
    public List<PcClass> getClasses() {
        List<PcClass> pcClasses = new ArrayList<>();
        String sql = "SELECT id, class_name\n" +
                "FROM classes;";
        try {
            SqlRowSet results = jdbc.queryForRowSet(sql);
            while (results.next()) {
                pcClasses.add(mapRowToClass(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return pcClasses;
    }

    @Override
    public List<PcClass> getClassesByCharacterId(int id) {
        List<PcClass> classes = new ArrayList<>();
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

    public PcClass mapRowToClass(SqlRowSet results) {
        return new PcClass(results.getString("class_name"), results.getInt("id"));
    }
}
