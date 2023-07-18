import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.mockito.Mockito.*;

public class MageControllerTest {
    private MageRepository mages;
    private MageController mageController;

    @BeforeEach
    private void init() {
        mages = Mockito.mock(MageRepository.class);
        mageController = new MageController(mages);
    }

    @Test
    public void deleteExist() {
        Assertions.assertEquals("done", mageController.delete("GandalfTester"));
    }

    @Test
    public void deleteNotExist() {
        doThrow(IllegalArgumentException.class)
                .when(mages)
                .delete(anyString());
        Assertions.assertEquals("not found", mageController.delete("GandalfTester"));
    }

    @Test
    public void findNotExist() {
        when(mages.find(anyString())).thenReturn(Optional.empty());
        Assertions.assertEquals("not found", mageController.find(anyString()));
    }

    @Test
    public void findExist() {
        String name = "GandalfTester";
        int level = 999;
        Mage expectedMage = new Mage(name, level);
        when(mages.find(name)).thenReturn(Optional.of(expectedMage));
        String actualMage = mageController.find(name);
        Assertions.assertEquals(expectedMage.toString(), actualMage);
    }

    @Test
    public void addNotExist() {
        Assertions.assertDoesNotThrow(() -> mageController.save("GandalfTester", 999));
        Assertions.assertEquals("done", mageController.save("GandalfTester", 999));
    }

    @Test
    public void addExist() {
        doThrow(IllegalArgumentException.class)
                .when(mages)
                .save(any(Mage.class));
        Assertions.assertEquals("bad request", mageController.save("GandalfTester", 999));
    }
}
