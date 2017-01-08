package cn.vpclub.spring.boot.demo;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class ApplicationIntegrationTests extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext context;

    @BeforeMethod
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    @Test(dependsOnMethods = "testLoginWithWrongPassword")
    public void testLogin() throws Exception {
        String expected = "用户 johnd 登录成功";

        given().
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
        String expected = "用户名或密码错误";

        given().
                contentType("text/plain; charset=UTF-8").
                param("username", "测试不存在的用户名").
                param("password", "测试错误密码").
        when().
                post("/login").
        then().
                statusCode(200).
                body("message", equalTo(expected));
    }

}
