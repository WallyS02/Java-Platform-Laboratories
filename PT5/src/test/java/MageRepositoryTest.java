import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MageRepositoryTest {
    @Test
    @DisplayName("Tests removing non existing object")
    public void removeNotExist() {
        MageRepository mages = new MageRepository();
        assertThrows(IllegalArgumentException.class, () -> {
           mages.delete("GandalfTester");
        });
    }

    @Test
    @DisplayName("Tests finding non existing object")
    public void findNotExist() {
        MageRepository mages = new MageRepository();
        assert mages.find("GandalfTester").isEmpty() : true;
    }

    @Test
    @DisplayName("Tests finding existing object")
    public void findExist() {
        MageRepository mages = new MageRepository();
        mages.save(new Mage("GandalfTester", 999));
        assert mages.find("GandalfTester").isPresent() : true;
    }

    @Test
    @DisplayName("Tests adding already existing object")
    public void addExist() {
        MageRepository mages = new MageRepository();
        mages.save(new Mage("GandalfTester", 999));
        assertThrows(IllegalArgumentException.class, () -> {
            mages.save(new Mage("GandalfTester", 999));
        });
    }
}
