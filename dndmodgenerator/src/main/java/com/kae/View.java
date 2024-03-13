package com.kae;

import com.kae.Models.PcClass;
import com.kae.Models.PlayerCharacter;

import java.util.List;

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
        List<PcClass> classes = pc.getClasses();
        StringBuilder classList = new StringBuilder();
        for (PcClass cl : classes) {
            classList.append(cl + "\n");
        }

        return String.format("(%d) %s\n" + "Race: %s\n" + "Level: %s\n" + "Classes: %s",
                pc.getId(), pc.getName(), pc.getCharRace(), pc.getLevel(), classList);
    }

}
