package cn.vpclub.spring.boot.demo;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@Slf4j
public class ApplicationIntegrationTests extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext context;

    @BeforeMethod
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    @Test(dataProvider = "login-test-cases")
    public void testLogin(String expected, String username, String password) throws Exception {

        given().
                param("username", username).
                param("password", password).
                log().all().
        when().
                post("/login").
        then().
                statusCode(200).
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
