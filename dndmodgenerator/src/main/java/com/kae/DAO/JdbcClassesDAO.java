package com.kae.DAO;

import com.kae.Exceptions.DaoException;
import com.kae.character.PCClass;
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
    public PCClass getClassById(int id) {
        PCClass pcClass = new PCClass();
        String sql = "SELECT id, class_name\n" +
                "FROM classes\n" +
                "WHERE id = ?";
        try {
            SqlRowSet results = jdbc.queryForRowSet(sql, id);
            if (results.next()) {
                pcClass = mapRowToPcClass(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return pcClass;
    }

    @Override
    public List<PCClass> getClasses() {
        List<PCClass> pcClasses = new ArrayList<>();
        String sql = "SELECT id, class_name\n" +
                "FROM classes;";
        try {
            SqlRowSet results = jdbc.queryForRowSet(sql);
            while (results.next()) {
                pcClasses.add(mapRowToPcClass(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return pcClasses;
    }

    public PCClass mapRowToPcClass(SqlRowSet results) {
        PCClass pcClass = new PCClass();
        pcClass.setId(results.getInt("id"));
        pcClass.setName(results.getString("class_name"));
        return pcClass;
    }
}
