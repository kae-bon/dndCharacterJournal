package com.kae.DAO;

import com.kae.Exceptions.DaoException;
import com.kae.Models.PlayerCharacter;
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
        PlayerCharacter pc = null;
        String sql = "SELECT id, name, race, level\n" +
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
        String sql = "SELECT id, name, race, level\n" +
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
    public PlayerCharacter createCharacter(PlayerCharacter character) {
        PlayerCharacter pc = null;
        String sql = "INSERT INTO characters(name, race, level)\n" +
                "VALUES (?, ?, ?)\n" +
                "RETURNING id;";
        try {
            int charId = jdbc.queryForObject(sql, int.class, character.getName(), character.getCharRace(), character.getLevel());
            pc = this.getCharacterById(charId);
//            linkCharacterClass(pc.getId(), charClass);
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
            int numRows = jdbc.update(sql, character.getName(), character.getCharRace(), character.getLevel(), character.getId());
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

    @Override
    public int deleteCharacterById(int id) {
        String sql = "DELETE FROM characters WHERE id = ?";
        try {
            return jdbc.update(sql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    public void linkCharacterClass(int characterId, String charClass) {
        String sql = "INSERT INTO character_class(character_id, class_id)\n" +
                "VALUES (?, (SELECT id FROM classes WHERE class_name ILIKE ?));";
        try {
            jdbc.update(sql, characterId, charClass);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    public PlayerCharacter mapRowToCharacter(SqlRowSet results) {
        PlayerCharacter character = new PlayerCharacter();
        character.setName(results.getString("name"));
        character.setId(results.getInt("id"));
        character.setCharRace(results.getString("race"));
        character.setLevel(results.getInt("level"));
        return character;
    }

}
