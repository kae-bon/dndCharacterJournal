package com.kae.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    Scanner userInput = new Scanner(System.in);
    public int promptUser(){
        System.out.println("Please choose an option: ");
        return Integer.parseInt(userInput.nextLine());
    }

    public void cont() {
        speak("Press enter to continue...");
    }

    public void speak(String message) {
        System.out.println(message);
    }

    public String promptForString(String field) {
        String property = userInput.nextLine();
        if (property.isBlank()) {
            speak(String.format("***%s field is required***", field));
        }
        return property;
    }

    public int promptForInt(String field) {
        int property = Integer.parseInt(userInput.nextLine());
        if (property == 0) {
            speak(String.format("***%s field is required", field));
        }
        return property;
    }

    public PlayerCharacter promptForNewCharacter() {
        PlayerCharacter pc = new PlayerCharacter();
        String name = "";
        String race = "";
        int level = 0;
        List<ClassModel> classes = new ArrayList<>();

        while (name.isBlank()) {
            speak("\nWhat is your character's name?");
            name = promptForString("Name");
        }
        pc.setName(name);

        while (race.isBlank()) {
            speak("\nWhat is your character's race?");
            race = promptForString("Race");
        }
        pc.setCharRace(race);

        while (level == 0) {
            speak("\nWhat is your character's level?");
            level = promptForInt("Level");
        }
        pc.setLevel(level);

        while (classes.isEmpty()) {
            speak("\nWhat are your character's classes?\n" +
                    "Please enter them separated by commas with no spaces.\n" +
                    "Example: Paladin,Bard");
            String classString = promptForString("Classes");

        }
        return pc;
    }

    public void displayCharacters(List<PlayerCharacter> playerCharacters) {
        speak("\nThese are your currently registered characters:");
        for (PlayerCharacter pc : playerCharacters) {
            speak(String.format("(%d) %s", pc.getId(), pc.getName()));
        }
        speak("");
    }

    public List<ClassModel> parseClassesIntoList(String classString) {
        List<ClassModel> classes = new ArrayList<>();
        String[] classArray = classString.split(",");
        for (String charClass : classArray) {

        }
        return classes;
    }

//    public void displayCharacter(PlayerCharacter pc) {
//        speak(String.format("\n(%d) %s\n" +
//                        "Class: %s\n" +
//                        "Level: %d\n" + "Race: %s\n",
//                        pc.getId(), pc.getName(), pc.getCharClass(),
//                        pc.getLevel(), pc.getCharRace()));
//    }

}
