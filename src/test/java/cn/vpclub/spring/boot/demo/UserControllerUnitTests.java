package cn.vpclub.spring.boot.demo;

import cn.vpclub.spring.boot.demo.service.UserService;
import cn.vpclub.spring.boot.demo.web.UserController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.powermock.api.mockito.PowerMockito.when;

@SpringBootTest
public class UserControllerUnitTests {

    private UserService userService;

    @BeforeMethod
    public void setUp() {
        userService = PowerMockito.mock(UserService.class);
        RestAssuredMockMvc.standaloneSetup(new UserController(userService));
    }

    @Test
    public void testLogin() throws Exception {
        String expected = "login success";

        when(userService.login("johnd", "123456")).
                thenReturn(expected);

        given().
                param("username", "johnd").
                param("password", "123456").
                log().all().
        when().
                get("/login").
        then().
                statusCode(200).
                body(containsString(expected));
    }

    @Test
    public void testLoginWithWrongPassword() throws Exception {
        String expected = "Wrong username or password";

        when(userService.login("testUser", "testPassword"))
                .thenReturn(expected);

        given().
                contentType("text/plain; charset=UTF-8").
                param("username", "testUser").
                param("password", "testPassword").
                log().all().
        when().
                get("/login").
        then().
                statusCode(200).
                body(containsString(expected));
    }

}
