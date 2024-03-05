package com.kae.Application;

import com.kae.DAO.JdbcCharacterDAO;
import com.kae.DAO.JdbcClassesDAO;
import com.kae.Models.ClassModel;
import com.kae.Models.PlayerCharacter;
import com.kae.Models.UserInterface;
import com.kae.View;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class GenerateCharacter {
    UserInterface ui = new UserInterface();
    JdbcCharacterDAO characterDAO;
    JdbcClassesDAO classDAO;
    View view = new View();

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
        ui.speak(view.welcome());
        boolean running = true;
        PlayerCharacter pc = null;

        while (running) {
            ui.speak(view.mainMenu());
            int userChoice = ui.promptUser();
            if (userChoice == 1) {
                pc = handlesRegisterCharacter();
                view.viewCharacter(pc);
            } else if (userChoice == 2) {
                ui.displayCharacters(characterDAO.getCharacters());
            } else if (userChoice == 3) {

            } else if (userChoice == 4) {

            } else if (userChoice == 5) {
                running = false;
            } else {
                ui.speak("\nThat's not a valid menu option!\n");
            }
        }
    }

    public PlayerCharacter handlesRegisterCharacter() {
        PlayerCharacter pc = ui.promptForNewCharacter();
        return characterDAO.createCharacter(pc);
    }

    public PlayerCharacter handlesCharacterRetrieval(int id) {
        return characterDAO.getCharacterById(id);
    }

    public ClassModel handlesClassRetrieval(int id) {
        return classDAO.getClassById(id);
    }


}
