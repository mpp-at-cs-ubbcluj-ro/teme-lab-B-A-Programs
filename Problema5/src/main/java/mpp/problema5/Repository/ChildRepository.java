package mpp.problema5.Repository;

import mpp.problema5.Domain.Child;
import mpp.problema5.Domain.Trial;

import java.util.Collection;
import java.util.List;

public interface ChildRepository extends Repository<Child, Long> {
    Child findByCnp(String cnp);

    List<Trial> getTrialsForChild(Child child);
}