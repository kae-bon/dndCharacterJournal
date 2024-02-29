import com.kae.DAO.JdbcCharacterDAO;
import com.kae.Models.PlayerCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JdbcCharacterDAOTests extends BaseDaoTests{
    private JdbcCharacterDAO jdbc;

    @Before
    public void setup() {
        jdbc = new JdbcCharacterDAO(dataSource);
    }

    private static final PlayerCharacter CHAR_RHYS = new PlayerCharacter(1, "Rhys", "Wood Elf", 12);
    private static final PlayerCharacter CHAR_SYLVE = new PlayerCharacter(2, "Sylve", "Tiefling", 10);
    private static final PlayerCharacter CHAR_NEME = new PlayerCharacter(3, "Neme", "Satyr", 5);
    private static final PlayerCharacter CHAR_CHICKEN = new PlayerCharacter(4, "Chicken", "Tiefling", 3);
    private static final PlayerCharacter CHAR_RINN = new PlayerCharacter(5, "Rinn", "Drow Elf", 12);

    @Test
    public void getCharacterById_with_valid_id_returns_correct_character() {
        PlayerCharacter pc = jdbc.getCharacterById(1);
        assertCharactersMatch(CHAR_RHYS, pc);

        pc = jdbc.getCharacterById(3);
        assertCharactersMatch(CHAR_NEME, pc);
    }

    @Test
    public void getCharacterById_invalid_id_returns_null() {
        PlayerCharacter pc = jdbc.getCharacterById(90);
        Assert.assertNull(pc);
    }

    @Test
    public void getCharacters_returns_list_of_chars() {
        List<PlayerCharacter> actual = jdbc.getCharacters();
        Assert.assertEquals(5, actual.size());
        assertCharactersMatch(actual.get(0), CHAR_RHYS);
        assertCharactersMatch(actual.get(4), CHAR_RINN);
    }

    @Test
    public void updateCharacter_updates_character() {
        PlayerCharacter pcToUpdate = jdbc.getCharacterById(1);
        pcToUpdate.setName("Durge");
        pcToUpdate.setLevel(pcToUpdate.getLevel() + 1);
        pcToUpdate.setCharRace("Bhaalspawn");

        PlayerCharacter updatedPc = jdbc.updateCharacter(pcToUpdate);
        Assert.assertNotNull(updatedPc);

        PlayerCharacter retrievedPc = jdbc.getCharacterById(1);
        assertCharactersMatch(pcToUpdate, retrievedPc);
        assertCharactersMatch(updatedPc, retrievedPc);
    }

    @Test
    public void createCharacter_creates_character() {
        PlayerCharacter character = new PlayerCharacter(0, "Quenneth", "Eladrin", 5);
        PlayerCharacter createdCharacter = jdbc.createCharacter(character);
        Assert.assertNotNull(createdCharacter);

        int newId = createdCharacter.getId();
        Assert.assertTrue(newId > 0);
        PlayerCharacter retrievedCharacter = jdbc.getCharacterById(newId);

        assertCharactersMatch(createdCharacter, retrievedCharacter);
    }

    @Test
    public void deleteCharacterById_deletes_character() {
        int rowsAffected = jdbc.deleteCharacterById(1);
        Assert.assertEquals(1, rowsAffected);
        PlayerCharacter retrievedCharacter = jdbc.getCharacterById(1);
        Assert.assertNull(retrievedCharacter);
    }

    private void assertCharactersMatch(PlayerCharacter expected, PlayerCharacter actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getLevel(), actual.getLevel());
        Assert.assertEquals(expected.getProficiencyBonus(), actual.getProficiencyBonus());
    }

}
