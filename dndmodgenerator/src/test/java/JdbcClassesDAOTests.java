import com.kae.DAO.JdbcClassesDAO;
import com.kae.Models.PcClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JdbcClassesDAOTests extends BaseDaoTests {
    private JdbcClassesDAO jdbc;
    private static final PcClass PALADIN = new PcClass("paladin", 1);
    private static final PcClass SORCERER = new PcClass("sorcerer", 4);
    private static final PcClass RANGER = new PcClass("ranger", 2);
    private static final PcClass DRUID = new PcClass("druid", 6);


    @Before
    public void setup() {
        jdbc = new JdbcClassesDAO(dataSource);
    }

    @Test
    public void getClassById_returns_correct() {
        PcClass cl = jdbc.getClassById(1);
        assertClassesMatch(PALADIN, cl);

        cl = jdbc.getClassById(4);
        assertClassesMatch(SORCERER, cl);

        cl = jdbc.getClassById(2);
        assertClassesMatch(RANGER, cl);

        cl = jdbc.getClassById(6);
        assertClassesMatch(DRUID, cl);
    }

    @Test
    public void getClassById_returns_null_invalid_id() {
        PcClass cl = jdbc.getClassById(20);
        Assert.assertNull(cl);
    }

    @Test
    public void getClassByName_returns_correct() {
        PcClass cl = jdbc.getClassByName("paladin");
        assertClassesMatch(PALADIN, cl);

        cl = jdbc.getClassByName("ranger");
        assertClassesMatch(RANGER, cl);

        cl = jdbc.getClassByName("druid");
        assertClassesMatch(DRUID, cl);
    }

    @Test
    public void getClasses_returns_list_of_classes() {
        List<PcClass> classes = jdbc.getClasses();
        Assert.assertEquals(13, classes.size());
        assertClassesMatch(classes.get(0), PALADIN);
        assertClassesMatch(classes.get(5), DRUID);
    }

    @Test
    public void getClassesByCharacterId_returns_correct_classes() {
        List<PcClass> classes = jdbc.getClassesByCharacterId(1);
        Assert.assertEquals(1, classes.size());
        assertClassesMatch(PALADIN, classes.get(0));

        classes = jdbc.getClassesByCharacterId(3);
        Assert.assertEquals(2, classes.size());
        assertClassesMatch(SORCERER, classes.get(0));
        assertClassesMatch(DRUID, classes.get(1));
    }

    private void assertClassesMatch(PcClass expected, PcClass actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

}
