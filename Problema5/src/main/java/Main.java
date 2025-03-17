import MPP.Domain.Child;
import MPP.Repository.ChildDBRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        ChildDBRepository childDBRepository = new ChildDBRepository(props);
        childDBRepository.add(new Child(0L, "5131313020202", "Georgica"));

        List<Child> children = childDBRepository.getAll();
        for (Child child : children) {
            System.out.println("COPILLLL: " + child);
        }
    }
}