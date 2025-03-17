package MPP.Repository;

import MPP.Domain.Child;
import MPP.Domain.Trial;

import java.util.Collection;
import java.util.List;

public interface ChildRepository extends Repository<Child, Long> {
    Child findByCnp(String cnp);

    List<Trial> getTrialsForChild(Child child);
}