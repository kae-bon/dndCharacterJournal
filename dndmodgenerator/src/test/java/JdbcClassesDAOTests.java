import com.kae.DAO.JdbcCharacterDAO;
import com.kae.DAO.JdbcClassesDAO;
import com.kae.Models.ClassModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JdbcClassesDAOTests extends BaseDaoTests {
    private JdbcClassesDAO jdbc;
    private static final ClassModel PALADIN = new ClassModel();
    private static final ClassModel SORCERER = new ClassModel();
    private static final ClassModel RANGER = new ClassModel();
    private static final ClassModel DRUID = new ClassModel();


    @Before
    public void setup() {
        jdbc = new JdbcClassesDAO(dataSource);
        PALADIN.setName("Paladin");
        PALADIN.setId(1);
        SORCERER.setName("Sorcerer");
        SORCERER.setId(4);
        RANGER.setName("Ranger");
        RANGER.setId(2);
        DRUID.setName("Druid");
        DRUID.setId(6);
    }

    @Test
    public void getClassById_returns_correct() {
        ClassModel cl = jdbc.getClassById(1);
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
        ClassModel cl = jdbc.getClassById(20);
        Assert.assertNull(cl);
    }

    @Test
    public void getClasses_returns_list_of_classes() {
        List<ClassModel> classes = jdbc.getClasses();
        Assert.assertEquals(13, classes.size());
        assertClassesMatch(classes.get(0), PALADIN);
        assertClassesMatch(classes.get(5), DRUID);
    }

    @Test
    public void getClassesByCharacterId_returns_correct_classes() {
        List<ClassModel> classes = jdbc.getClassesByCharacterId(1);
        Assert.assertEquals(1, classes.size());
        assertClassesMatch(PALADIN, classes.get(0));

        classes = jdbc.getClassesByCharacterId(3);
        Assert.assertEquals(2, classes.size());
        assertClassesMatch(SORCERER, classes.get(0));
        assertClassesMatch(DRUID, classes.get(1));
    }

    private void assertClassesMatch(ClassModel expected, ClassModel actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

}
