package com.kae.DAO;

import com.kae.Exceptions.DaoException;
import com.kae.character.PlayerCharacter;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcCharacterDAO implements CharacterDAO{
    private final JdbcTemplate jdbc;

//    private String connection = "jdbc.postgresql://localhost:5432/DnD";
    public JdbcCharacterDAO(DataSource datasource) {
        this.jdbc = new JdbcTemplate(datasource);
    }

    @Override
    public PlayerCharacter getCharacterById(int id) {
        PlayerCharacter pc = new PlayerCharacter();
        String sql = "SELECT id, name, race, level, class_id\n" +
                "FROM characters\n" +
                "WHERE id = ?;";
        try {
            SqlRowSet results = jdbc.queryForRowSet(sql, id);
            if (results.next()) {
                pc = mapRowToCharacter(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return pc;
    }

    @Override
    public List<PlayerCharacter> getCharacters() {
        List<PlayerCharacter> characters = new ArrayList<>();
        String sql = "SELECT id, name, race, level, class_id\n" +
                "FROM characters;";
        try {
            SqlRowSet results = jdbc.queryForRowSet(sql);
            while (results.next()) {
                characters.add(mapRowToCharacter(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return characters;
    }

    @Override
    public PlayerCharacter createCharacter(PlayerCharacter character, String charClass) {
        PlayerCharacter pc = null;
        String sql = "INSERT INTO characters(name, race, level, class_id)\n" +
                "VALUES (?, ?, ?, (SELECT id FROM classes WHERE class_name = ?))\n" +
                "RETURNING id;";
        try {
            int charId = jdbc.queryForObject(sql,
                    int.class,
                    character.getName(),
                    character.getCharRace(),
                    character.getLevel(),
                    charClass);
            pc = this.getCharacterById(charId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return pc;
    }

    @Override
    public PlayerCharacter updateCharacter(PlayerCharacter character) {
        PlayerCharacter pc = null;
        String sql = "UPDATE characters\n" +
                "SET name=?, race=?, level=?\n" +
                "WHERE id = ?;";
        try {
            int numRows = jdbc.update(sql, character.getName(), character.getCharRace(), character.getLevel());
            if (numRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                pc = getCharacterById(character.getId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return pc;
    }

    public void linkCharacterClass(int characterId, int classId) {
        String sql = "INSERT INTO character_class(character_id, class_id)\n" +
                "VALUES (?, ?);";
        try {
            jdbc.update(sql, characterId, classId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {             // these would be errors from foreign key constraints
            throw new DaoException("Data integrity violation", e);
        }
    }

    public PlayerCharacter mapRowToCharacter(SqlRowSet results) {
        PlayerCharacter character = new PlayerCharacter();
        character.setName(results.getString("name"));
        character.setId(results.getInt("id"));
        character.setCharRace(results.getString("race"));
        character.setLevel(results.getInt("level"));
        character.setClassId(results.getInt("class_id"));
        return character;
    }

}
