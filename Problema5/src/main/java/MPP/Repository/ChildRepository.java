package MPP.Repository;

import MPP.Domain.Child;

import java.util.Collection;

public interface ChildRepository extends Repository<Child, Long> {
    Collection<Child> getChildrenByTrial(Long trialId);
}