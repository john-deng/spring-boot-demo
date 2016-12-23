package cn.vpclub.spring.boot.demo.service;

import cn.vpclub.spring.boot.demo.storage.domain.User;
import cn.vpclub.spring.boot.demo.storage.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Created by johnd on 23/12/2016.
 */
@Component
public class UserServiceImp implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

    private UserMapper userMapper;

    @Autowired
    public UserServiceImp(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public String login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        String retVal = "用户名或密码错误";
        if ( user.getPassword().equals(password) )
        {
            retVal = "用户 " + username + " 登录成功";
        }

        logger.info(retVal + " " + Instant.now());

        return retVal;
    }
}
