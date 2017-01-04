package cn.vpclub.spring.boot.demo;

import cn.vpclub.spring.boot.demo.service.UserInput;
import cn.vpclub.spring.boot.demo.service.UserService;
import cn.vpclub.spring.boot.demo.service.UserServiceImpl;
import cn.vpclub.spring.boot.demo.storage.domain.User;
import cn.vpclub.spring.boot.demo.storage.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

/**
 * Created by johnd on 23/12/2016.
 */
@SpringBootTest
public class UserServiceTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.userService = new UserServiceImpl(this.userMapper);
    }

    @Test
    public void testSignUp() {
        String username = "johnd";
        String password = "123456";
        String expected = "用户 " + username + " 注册成功";

        UserInput userInput = new UserInput();
        userInput.setUsername(username);
        userInput.setPassword(password);

        given(userMapper.insert(any()))
                .willReturn(1);

        String result = userService.signUp(userInput);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testLogin() {
        String username = "johnd";
        String password = "123456";
        String expected = "用户 " + username + " 登录成功";

        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword(password);

        given(userMapper.selectByUsername(anyString()))
                .willReturn(user);

        String result = userService.login(username, password);
        Assert.assertEquals(expected, result);
    }
}
