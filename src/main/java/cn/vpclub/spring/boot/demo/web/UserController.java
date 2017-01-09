package cn.vpclub.spring.boot.demo.web;

import cn.vpclub.spring.boot.demo.domain.UserRequest;
import cn.vpclub.spring.boot.demo.domain.UserResponse;
import cn.vpclub.spring.boot.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 * Created by johnd on 23/12/2016.
 */
@RestController
@Slf4j
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public UserResponse login(@RequestBody UserRequest userRequest) {
        log.info("Entering login with username: {} password: {}", userRequest.getUsername(), userRequest.getPassword());
        String result = userService.login(userRequest.getUsername(), userRequest.getPassword());

        UserResponse response = new UserResponse();
        response.setMessage(result);

        log.info("End of login with result: {}", result);
        return response;
    }
}
