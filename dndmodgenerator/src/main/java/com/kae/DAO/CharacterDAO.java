package com.kae.DAO;

import com.kae.Models.PlayerCharacter;

import java.util.List;

public interface CharacterDAO {
    /**
    * Gets character from database by id
    **/
    public PlayerCharacter getCharacterById(int id);
    /**
     * Gets list of all characters from database
     **/
    public List<PlayerCharacter> getCharacters();
    /**
     * Adds new character to database
     **/
    public PlayerCharacter createCharacter(PlayerCharacter character);
    /**
     * Updates pre-existing character
     **/
    public PlayerCharacter updateCharacter(PlayerCharacter character);
    /**
     * Deletes existing character by id
    **/
    public int deleteCharacterById(int id);
}
