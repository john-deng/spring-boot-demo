package cn.vpclub.spring.boot.demo;

import cn.vpclub.spring.boot.demo.domain.UserRequest;
import cn.vpclub.spring.boot.demo.service.UserService;
import cn.vpclub.spring.boot.demo.web.UserController;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.powermock.api.mockito.PowerMockito.when;

@SpringBootTest
public class UserControllerTests {

    private UserService userService;

    @BeforeMethod
    public void setUp() {
        userService = PowerMockito.mock(UserService.class);
        RestAssuredMockMvc.standaloneSetup(new UserController(userService));
    }

    @Test(dataProvider = "login-test-cases")
    public void testLogin(String expected, String username, String password) throws Exception {

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);

        when(userService.login(username, password)).
                thenReturn(expected);
        given().
                contentType(ContentType.JSON).
                body(userRequest).
                log().all().
        when().
                post("/login").
        then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("message", equalTo(expected));
    }

    @DataProvider(name = "login-test-cases")
    public Object[][] loginData() {
        return new Object[][] {
                {"用户 johnd 登录成功", "johnd", "123456"},
                {"用户名或密码错误", "johnd", "错误的密码"},
                {"用户名或密码错误", "测试不存在的用户名", "123456"},
        };
    }
}
