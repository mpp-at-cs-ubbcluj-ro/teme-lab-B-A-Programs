package services;

import domain.Child;

public interface IEnrollmentService {
    public int getChildEnrollmentsNumber(Child child);
    public void enrollChild(long trialId, Child child) throws Exception;
}
