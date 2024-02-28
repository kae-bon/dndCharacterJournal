package com.kae.character;

import com.kae.abilityscores.AbilityScore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerCharacter {

    private int id;
    private String name;
    private String charClass;
    private String charRace;
    private int level;

    private int classId;

    private
    List<AbilityScore> abilityScores = new ArrayList<>();

    // Constructor

    public PlayerCharacter(String name, String startingClass, String race, int level) {
        this.name = name;
        this.charClass = startingClass;
        this.charRace = race;
        this.level = level;
    }
    public PlayerCharacter(){}

    public void setAbilityScore(String ability, int score) {
        AbilityScore abscore = new AbilityScore();
        abscore.setAbility(ability);
        abscore.setAbilityScore(score);
        abilityScores.add(abscore);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharClass() {
        return charClass;
    }

    public void setCharClass(String startingClass) {
        this.charClass = startingClass;
    }

    public List<AbilityScore> getAbilityScores() {
        return abilityScores;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharRace() {
        return charRace;
    }

    public void setCharRace(String charRace) {
        this.charRace = charRace;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void raiseLevelByOne() {
        if (level + 1 <= 20) {
            this.level = this.level + 1;
        } else {
            System.out.println("Character levels can't go above 20, sorry!");
        }
    }

    public void characterCreated() {
        System.out.println("You have registered: \n" + "Name: " + name + "\nRace: " + charRace + "\nClass: " + charClass + "\nLevel: " + level);
    }

    public int getProficiencyBonus() {
        int proficiencyBonus = 0;
        if (level >= 1 && level <= 4) {
            proficiencyBonus = 2;
        } else if (level <= 8) {
            proficiencyBonus = 3;
        } else if (level <= 12) {
            proficiencyBonus = 4;
        } else if (level <= 16) {
            proficiencyBonus = 5;
        } else if (level <= 20) {
            proficiencyBonus = 6;
        } return proficiencyBonus;
    }


}