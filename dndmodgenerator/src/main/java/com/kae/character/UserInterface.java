package com.kae.character;

import com.kae.DAO.JdbcClassesDAO;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    Scanner userInput = new Scanner(System.in);
    public int promptUser(){
        System.out.println("Please choose an option: ");
        return Integer.parseInt(userInput.nextLine());
    }

    public void speak(String message) {
        System.out.println(message);
    }

    public String promptForString() {
        return userInput.nextLine();
    }

    public int promptForInt() {
        return Integer.parseInt(userInput.nextLine());
    }

    public PlayerCharacter promptForNewCharacter() {
        PlayerCharacter pc = new PlayerCharacter();
        speak("\nWhat is your character's name?");

        String name = "";
        while (name.isBlank()) {
            name = promptForString();
        }
        pc.setName(name);

        speak("\nWhat is your character's race?");
        String race = "";
        while (race.isBlank()) {
            race = promptForString();
        }
        pc.setCharRace(race);

        speak("\nWhat is your character's current level? (This can be updated later)");
        int level = 0;
        while (level == 0) {
            level = promptForInt();
        }
        pc.setLevel(level);

        speak("\nWhat is your character's class?");
        String charClass = "";
        while (charClass.isBlank()) {
            charClass =promptForString();
        }
        pc.setCharClass(charClass);

        return pc;
    }

    public void displayCharacters(List<PlayerCharacter> playerCharacters) {
        speak("\nThese are your currently registered characters:");
        for (PlayerCharacter pc : playerCharacters) {
            speak(String.format("(%d) %s", pc.getId(), pc.getName()));
        }
        speak("");
    }

    public void displayCharacter(PlayerCharacter pc, PCClass charClass) {
        speak(String.format("\nCharacter: %s\n" +
                        "Id: %d\n" +
                        "Class: %s\n" +
                        "Level: %d\n" + "Race: %s\n",
                        pc.getName(), pc.getId(), charClass.getName(),
                        pc.getLevel(), pc.getCharRace()));
    }

}
