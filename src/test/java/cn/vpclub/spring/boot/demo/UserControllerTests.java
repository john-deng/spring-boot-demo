package cn.vpclub.spring.boot.demo;

import cn.vpclub.spring.boot.demo.service.UserService;
import cn.vpclub.spring.boot.demo.web.UserController;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static java.nio.charset.StandardCharsets.UTF_8;
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

    @Test
    public void testLogin() throws Exception {
        String expected = "login success 登录成功";

        when(userService.login("johnd", "123456")).
                thenReturn(expected);

        given().
                contentType(ContentType.JSON.withCharset(UTF_8)).
                param("username", "johnd").
                param("password", "123456").

                log().all().
        when().
                post("/login").
        then().
                statusCode(200).
                body("message", equalTo(expected));
    }

    @Test
    public void testLoginWithWrongPassword() throws Exception {
        String expected = "Wrong username or password 错误的用户名或密码";

        when(userService.login("testUser", "testPassword"))
                .thenReturn(expected);

        given().
                param("username", "testUser").
                param("password", "testPassword").
                log().all().
        when().
                post("/login").
        then().
                statusCode(200).
                body("message", equalTo(expected));
    }

}
