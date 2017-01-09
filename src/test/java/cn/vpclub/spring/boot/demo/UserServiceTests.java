package cn.vpclub.spring.boot.demo;

import cn.vpclub.spring.boot.demo.domain.UserRequest;
import cn.vpclub.spring.boot.demo.service.UserService;
import cn.vpclub.spring.boot.demo.service.UserServiceImpl;
import cn.vpclub.spring.boot.demo.storage.domain.User;
import cn.vpclub.spring.boot.demo.storage.mapper.UserMapper;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by johnd on 23/12/2016.
 */
@SpringBootTest
public class UserServiceTests {

    private UserService userService;
    private UserMapper userMapper;
    private User testUser;
    private UserRequest userInput;

    private String username = "johnd";
    private String password = "123456";

    @BeforeMethod
    public void setup() {
        userMapper = PowerMockito.mock(UserMapper.class);
        userService = new UserServiceImpl(this.userMapper);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername(username);
        testUser.setPassword(password);

        userInput = new UserRequest();
        userInput.setUsername(username);
        userInput.setPassword(password);
    }

    @Test
    public void testSignUp() {
        String expected = "用户 " + username + " 注册成功";

        when(userMapper.selectByUsername(username)).thenReturn(null);

        when(userMapper.insert(any())).thenReturn(1);

        String result = userService.signUp(userInput);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testSignUpWhenUserExist() {
        String expected = "用户 " + username + " 注册失败";

        when(userMapper.selectByUsername(username)).thenReturn(testUser);

        when(userMapper.insert(any())).thenReturn(0);

        String result = userService.signUp(userInput);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testLogin() {
        String expected = "用户 " + username + " 登录成功";

        when(userMapper.selectByUsername(username)).thenReturn(testUser);

        String result = userService.login(username, password);

        //Verifying that userService did call the
        //selectByUsername method on the mocked service
        //instance userMapper.
        Mockito.verify(userMapper).selectByUsername(username);
        Mockito.verify(userMapper, times(1)).selectByUsername(username);

        Assert.assertEquals(result, expected);
    }
}
