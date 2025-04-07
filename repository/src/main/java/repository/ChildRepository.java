package repository;

import domain.Child;
import domain.Trial;

import java.util.List;

public interface ChildRepository extends Repository<Child, Long> {
    Child findByCnp(String cnp);

    List<Trial> getTrialsForChild(Child child);
}