package MPP.Repository;

import MPP.Domain.Child;
import MPP.Domain.Trial;

public interface TrialRepository extends Repository<Trial, Long> {
    void addChild(Child child);
}
