import model.ComputerRepairRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.RequestRepository;
import com.google.common.collect.Iterables;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ComputerRepairRequestTest {
    @Test
    @DisplayName("Test Add")
    public void testAdd() {
        RequestRepository repo = new RequestRepository();
        repo.add(new ComputerRepairRequest(1,"A A","Address A","072222","Asus","13/10/2020","Broken display"));
        repo.add(new ComputerRepairRequest(2,"B B","Address B","072222","Acer","10/10/2020","Faulty keyboard"));
        assertEquals(Iterables.size(repo.findAll()), 2);
        assertEquals(repo.findById(1).getOwnerName(), "A A");
    }

    @Test
    @DisplayName("Test Delete")
    public void testDelete() {
        RequestRepository repo = new RequestRepository();
        repo.add(new ComputerRepairRequest(1,"A A","Address A","072222","Asus","13/10/2020","Broken display"));
        repo.add(new ComputerRepairRequest(2,"B B","Address B","072222","Acer","10/10/2020","Faulty keyboard"));
        assertEquals(Iterables.size(repo.findAll()), 2);
        repo.delete(new ComputerRepairRequest(1,"A A","Address A","072222","Asus","13/10/2020","Broken display"));
        assertEquals(Iterables.size(repo.findAll()), 1);
        assertEquals(repo.findById(2).getOwnerName(), "B B");
    }
}
