package cn.vpclub.spring.boot.demo.web;

import cn.vpclub.spring.boot.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by johnd on 23/12/2016.
 */
@RestController
@Slf4j
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(String username, String password) {
        log.info("Entering login with username: {} password: {}", username, password);
        String result = userService.login(username, password);
        log.info("End of login with result: {}", result);
        return result;
    }
}
