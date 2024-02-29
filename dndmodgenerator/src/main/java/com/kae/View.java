package com.kae;

import com.kae.Models.PlayerCharacter;

public class View {

    public String welcome() {
        return String.format("-----------------------------------------\n" +
                "| Welcome to the D&D Character Journal! |\n" +
                "-----------------------------------------\n");
    }

    public String mainMenu() {
        return String.format("What would you like to do?\n" +
                "(1) Register Character\n" +
                "(2) Access Character\n" +
                "(3) Get Random Character\n" +
                "(4) Get List of Characters\n" +
                "(5) Exit\n");
    }

    public String viewCharacter(PlayerCharacter pc) {
        return String.format("(%f) %s\n" + "Race: %s\n" + "Level: %s\n",
                pc.getId(), pc.getName(), pc.getCharRace(), pc.getLevel());
    }

}
