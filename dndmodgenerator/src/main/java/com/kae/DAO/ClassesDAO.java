package com.kae.DAO;

import com.kae.character.PCClass;

import java.util.List;

public interface ClassesDAO {
    /**
     * Gets class by id
     **/
    public PCClass getClassById(int id);
    /**
     * Gets list of all character classes
     **/
    public List<PCClass> getClasses();

}
