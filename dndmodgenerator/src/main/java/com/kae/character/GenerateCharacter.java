package com.kae.character;

import com.kae.DAO.CharacterDAO;
import com.kae.DAO.JdbcCharacterDAO;
import com.kae.DAO.JdbcClassesDAO;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.Scanner;

public class GenerateCharacter {
    UserInterface ui = new UserInterface();
    JdbcCharacterDAO characterDAO;
    JdbcClassesDAO classDAO;

    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/DnD");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        GenerateCharacter generateCharacter = new GenerateCharacter(dataSource);
        generateCharacter.run();
    }

    public GenerateCharacter(DataSource dataSource) {
        characterDAO = new JdbcCharacterDAO(dataSource);
        classDAO = new JdbcClassesDAO(dataSource);
    }

    public void run() {
        ui.speak(welcome());
        boolean running = true;
        PlayerCharacter pc;
        while (running) {
            ui.speak(mainMenu());
            int userChoice = ui.promptUser();
            if (userChoice == 1) {
                pc = ui.promptForNewCharacter();
                handlesNewCharacterCreation(pc);
            } else if (userChoice == 2) {
                ui.displayCharacters(characterDAO.getCharacters());
                pc = characterDAO.getCharacterById(ui.promptForInt());
                PCClass charClass = handlesClassRetrieval(pc.getClassId());
                ui.displayCharacter(pc, charClass);
            } else if (userChoice == 3) {

            } else if (userChoice == 4) {

            } else if (userChoice == 5) {
                running = false;
            } else {
                ui.speak("\nThat's not a valid menu option!\n");
            }
        }
    }

    public String mainMenu() {
        return String.format("What would you like to do?\n" +
                "(1) Register Character\n" +
                "(2) Access Character\n" +
                "(3) Get Random Character\n" +
                "(4) Get List of Characters\n" +
                "(5) Exit\n");
    }

    public String welcome() {
        return String.format("-----------------------------------------\n" +
                "| Welcome to the D&D Character Creator! |\n" +
                "-----------------------------------------\n");
    }

    public void handlesNewCharacterCreation(PlayerCharacter pc) {
        characterDAO.createCharacter(pc, pc.getCharClass());
    }

    public PlayerCharacter handlesCharacterRetrieval(int id) {
        return characterDAO.getCharacterById(id);
    }

    public PCClass handlesClassRetrieval(int id) {
        return classDAO.getClassById(id);
    }


}
