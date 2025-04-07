package services;

import domain.User;

public interface IUserService {
    public void LogIn(User user) throws Exception;

    public void logOut(IObserver observer) throws Exception;
}