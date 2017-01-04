package cn.vpclub.spring.boot.demo.service;

/**
 * Created by johnd on 23/12/2016.
 */

public interface UserService {
    public String signUp(UserInput userInput);
    public String login(String username, String password);
}
