package com.kae.Models;

import com.kae.Exceptions.AbilityScoreException;

public class AbilityScore {
    private int score;
    public enum Ability {
        STRENGTH, CONSTITUTION, DEXTERITY, WISDOM, INTELLIGENCE, CHARISMA
    }
    private Ability ability;

    public AbilityScore(){}

    public String getAbility() {
        return ability.toString();
    }

    public void setAbility(String ability) {
        if (ability.equalsIgnoreCase("strength")) {
            this.ability = Ability.STRENGTH;
        } else if (ability.equalsIgnoreCase("constitution")) {
            this.ability = Ability.CONSTITUTION;
        } else if (ability.equalsIgnoreCase("dexterity")) {
            this.ability = Ability.DEXTERITY;
        } else if (ability.equalsIgnoreCase("wisdom")) {
            this.ability = Ability.WISDOM;
        } else if (ability.equalsIgnoreCase("intelligence")) {
            this.ability = Ability.INTELLIGENCE;
        } else if (ability.toString().equalsIgnoreCase("charisma")) {
            this.ability = Ability.CHARISMA;
        }
    }

    public int getAbilityScore() {
        return this.score;
    }

    public void setAbilityScore(int score) {
        if (isValidScore(score)) {
            this.score = score;
        } else {
            throw new AbilityScoreException("That is not a valid ability score.");
        }
    }

    public boolean isValidScore(int score) {
        return score <= 20 && score > 0;
    }

    public void raiseAbilityScore(int amountToRaiseBy) {
        if (isValidScore(amountToRaiseBy + this.score)) {
            this.score = this.score + amountToRaiseBy;
        } else {
            throw new AbilityScoreException("Ability scores cannot go above 20.");
        }
    }

    public void lowerAbilityScore(int amountToLowerBy) {
        if (isValidScore(this.score - amountToLowerBy)) {
            this.score = this.score - amountToLowerBy;
        } else {
            throw new AbilityScoreException("Ability scores cannot go below 0.");
        }
    }

    public String toString() {
        return this.ability.toString() + " score is " + this.score;
    }

    public int getAbilityModifier() {
        int modifier = 0;

            if (this.score == 1) {
                modifier = -5;
            } else if (this.score <= 3) {
                modifier = -4;
            } else if (this.score <= 5) {
                modifier = -3;
            } else if (this.score <= 7) {
                modifier = -2;
            } else if (this.score <= 9) {
                modifier = -1;
            } else if (this.score <= 11) {
                modifier = 0;
            } else if (this.score <= 13) {
                modifier = 1;
            } else if (this.score <= 15) {
                modifier = 2;
            } else if (this.score <= 17) {
                modifier = 3;
            } else if (this.score <= 19) {
                modifier = 4;
            } else if (this.score == 20) {
                modifier = 5;
            }

        return modifier;
    }


}
