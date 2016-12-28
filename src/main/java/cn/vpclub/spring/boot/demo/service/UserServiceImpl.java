package cn.vpclub.spring.boot.demo.service;

import cn.vpclub.spring.boot.demo.storage.domain.User;
import cn.vpclub.spring.boot.demo.storage.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * UserServiceImp
 * Created by johnd on 23/12/2016.
 */
@Component
@Slf4j
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public String login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        String retVal = "用户名或密码错误";

        if ( null != user && user.getPassword().equals(password) )
        {
            retVal = "用户 " + username + " 登录成功";
        }

        log.info("DEBUG: {} is logging in, {}", username, retVal);

        return retVal;
    }
}
