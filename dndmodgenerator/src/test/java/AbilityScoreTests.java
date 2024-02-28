import com.kae.abilityscores.AbilityScore;
import com.kae.abilityscores.AbilityScoreException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AbilityScoreTests {

    AbilityScore testScore = null;

    @Before
    public void setup() {
        testScore = new AbilityScore();
    }

    @Test
    public void set_ability_sets_correct_ability() {
        testScore.setAbility("strength");
        Assert.assertTrue(testScore.getAbility().equalsIgnoreCase("strength"));

        testScore.setAbility("dexterity");
        Assert.assertTrue(testScore.getAbility().equalsIgnoreCase("dexterity"));

        testScore.setAbility("wisdom");
        Assert.assertTrue(testScore.getAbility().equalsIgnoreCase("wisdom"));

        testScore.setAbility("intelligence");
        Assert.assertTrue(testScore.getAbility().equalsIgnoreCase("intelligence"));

        testScore.setAbility("charisma");
        Assert.assertTrue(testScore.getAbility().equalsIgnoreCase("charisma"));

        testScore.setAbility("constitution");
        Assert.assertTrue(testScore.getAbility().equalsIgnoreCase("constitution"));
    }

    @Test
    public void isValidScore_valid_score() {
        Assert.assertTrue(testScore.isValidScore(15));
        Assert.assertTrue(testScore.isValidScore(1));
        Assert.assertTrue(testScore.isValidScore(20));
    }

    @Test
    public void isValidScore_invalid_score() {
        Assert.assertFalse(testScore.isValidScore(0));
        Assert.assertFalse(testScore.isValidScore(21));
        Assert.assertFalse(testScore.isValidScore(-10));
    }

    @Test
    public void ability_modifier_returns_correct() {
        testScore.setAbilityScore(10);
        Assert.assertEquals(0, testScore.getAbilityModifier());

        testScore.setAbilityScore(1);
        Assert.assertEquals(-5, testScore.getAbilityModifier());

        testScore.setAbilityScore(20);
        Assert.assertEquals(5, testScore.getAbilityModifier());
    }

    @Test
    public void raise_ability_score() {
        testScore.setAbilityScore(10);
        testScore.raiseAbilityScore(5);
        Assert.assertEquals(15, testScore.getAbilityScore());

        testScore.raiseAbilityScore(5);
        Assert.assertEquals(20, testScore.getAbilityScore());
    }

    @Test
    public void lower_ability_score() {
        testScore.setAbilityScore(10);
        testScore.lowerAbilityScore(5);
        Assert.assertEquals(5, testScore.getAbilityScore());
    }

}
