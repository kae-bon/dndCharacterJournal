package com.kae.DAO;

import com.kae.Models.PcClass;

import java.util.List;

public interface ClassesDAO {
    /**
     * Gets class by id
     **/
    public PcClass getClassById(int id);
    /**
     * Gets list of all character classes
     **/
    public List<PcClass> getClasses();

    public List<PcClass> getClassesByCharacterId(int id);

}
