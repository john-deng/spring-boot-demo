package cn.vpclub.spring.boot.demo.service;

import cn.vpclub.spring.boot.demo.domain.UserRequest;

/**
 * Created by johnd on 23/12/2016.
 */

public interface UserService {
    public String signUp(UserRequest userInput);
    public String login(String username, String password);
}
