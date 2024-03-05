package com.kae.DAO;

import com.kae.Models.ClassModel;

import java.util.List;

public interface ClassesDAO {
    /**
     * Gets class by id
     **/
    public ClassModel getClassById(int id);
    /**
     * Gets list of all character classes
     **/
    public List<ClassModel> getClasses();

    public List<ClassModel> getClassesByCharacterId(int id);

}
