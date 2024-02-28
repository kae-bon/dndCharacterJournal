import com.kae.abilityscores.AbilityScore;
import com.kae.character.PlayerCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerCharacterTests {

    PlayerCharacter character = null;

    @Before
    public void setup() {
        character = new PlayerCharacter("Rhys", "Paladin", "Wood Elf", 12);
    }

    public List<AbilityScore> createAbilityScoreList() {
        List<AbilityScore> scores = new ArrayList<>();
        AbilityScore strength = new AbilityScore();
        strength.setAbilityScore(10);
        AbilityScore dex = new AbilityScore();
        dex.setAbilityScore(10);
        AbilityScore wis = new AbilityScore();
        wis.setAbilityScore(10);
        AbilityScore intel = new AbilityScore();
        intel.setAbilityScore(10);
        AbilityScore charisma = new AbilityScore();
        charisma.setAbilityScore(10);
        AbilityScore con = new AbilityScore();
        con.setAbilityScore(10);
        scores.add(strength);
        scores.add(dex);
        scores.add(wis);
        scores.add(intel);
        scores.add(charisma);
        scores.add(con);
        return scores;
    }

    @Test
    public void raise_level_by_one() {
        character.raiseLevelByOne();
        Assert.assertEquals(13, character.getLevel());
    }

    @Test
    public void raise_level_cant_go_above_20() {
        PlayerCharacter char2 = new PlayerCharacter("XX", "XX", "XX", 19);
        char2.raiseLevelByOne();
        Assert.assertEquals(20, char2.getLevel());

        char2.raiseLevelByOne();
        Assert.assertEquals(20, char2.getLevel());
    }

    @Test
    public void calc_proficiency_bonus() {
        int actual = character.getProficiencyBonus();
        Assert.assertEquals(4, actual);

        PlayerCharacter highLevelChar = new PlayerCharacter("X", "X", "X", 20);
        actual = highLevelChar.getProficiencyBonus();
        Assert.assertEquals(6, actual);
    }

}
