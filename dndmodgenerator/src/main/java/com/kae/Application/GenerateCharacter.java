package com.kae.Application;

import com.kae.DAO.JdbcCharacterDAO;
import com.kae.DAO.JdbcClassesDAO;
import com.kae.Models.PcClass;
import com.kae.Models.PlayerCharacter;
import com.kae.Models.UserInterface;
import com.kae.View;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class GenerateCharacter {
    UserInterface ui = new UserInterface();
    View view = new View();
    JdbcCharacterDAO characterDAO;
    JdbcClassesDAO classDAO;

    public static void main(String[] args) {
        GenerateCharacter generateCharacter = new GenerateCharacter();
        generateCharacter.run();
    }

    public GenerateCharacter() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/DnD");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        characterDAO = new JdbcCharacterDAO(dataSource);
        classDAO = new JdbcClassesDAO(dataSource);
    }

    public void run() {
        ui.speak(view.welcome());
        boolean running = true;
        PlayerCharacter pc = null;

        while (running) {
            ui.speak(view.mainMenu());
            int userChoice = ui.promptUser();
            if (userChoice == 1) {
                pc = handlesRegisterCharacter();
                ui.speak(view.viewCharacter(pc));
            } else if (userChoice == 2) {
                ui.displayCharacters(characterDAO.getCharacters());
            } else if (userChoice == 3) {
                pc = handlesCharacterRetrieval();
                ui.speak(view.viewCharacter(pc));
            } else if (userChoice == 4) {
            } else if (userChoice == 5) {
                running = false;
            } else {
                ui.speak("\nThat's not a valid menu option!\n");
            }
        }
    }

    public PlayerCharacter handlesRegisterCharacter() {
        String name = "";
        String race = "";
        int level = 0;

        while (name.isBlank()) {
            ui.speak("What is your character's name?");
            name = ui.promptForString("name");
        }

        while (race.isBlank()) {
            ui.speak("What is your character's race?");
            race = ui.promptForString("race");
        }

        while (level == 0) {
            ui.speak("What is your character's current level?");
            level = ui.promptForInt("level");
        }

        ui.speak("What are your character's current classes? If they are multiclassed, separate the classes with commas. Ex. Paladin,Bard");
        String[] classArray = ui.promptForString("class").toLowerCase().split(",");
        List<PcClass> classes = new ArrayList<>();

        PlayerCharacter pc = characterDAO.createCharacter(new PlayerCharacter(0, name, race, level));
        int charId = pc.getId();

        for (String cl : classArray) {
            PcClass pcClass = classDAO.getClassByName(cl.trim());
            if (!classes.contains(pcClass)) {
                classes.add(pcClass);
                characterDAO.linkCharacterClass(charId, cl.trim());
            }
        }
        pc.setClasses(classes);

        return pc;
    }

    public PlayerCharacter handlesCharacterRetrieval() {
        int id = ui.promptForInt("character id");
        return characterDAO.getCharacterById(id);
    }

    public List<PcClass> handlesClassRetrieval(int id) {
        return classDAO.getClassesByCharacterId(id);
    }


}
