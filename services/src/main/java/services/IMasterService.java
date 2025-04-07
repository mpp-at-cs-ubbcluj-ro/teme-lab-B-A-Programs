package services;

import domain.Child;
import domain.DTO.TrialDTO;
import domain.User;

import java.util.List;

public interface IMasterService {
    public void enrollChild(long trialId, Child child) throws Exception;

    public List<TrialDTO> getTrials() throws Exception;

    public void logIn(User user, IObserver observer) throws Exception;

    public void logOut(IObserver observer) throws Exception;
}
