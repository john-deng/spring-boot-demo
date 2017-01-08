package cn.vpclub.spring.boot.demo;

import lombok.extern.slf4j.Slf4j;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
public class ApplicationMockMvcIntegrationTests extends AbstractTestNGSpringContextTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test(dependsOnMethods = "testLoginWithWrongPassword")
	public void testLogin() throws Exception {

		String expected = "登录成功";
		String username = "johnd";
		String password = "123456";

		log.info("\n\n测试用例：{} 用户名：{} 密码：{}", expected, username, password);

		mockMvc.perform(get("/login?username=" + username + "&password=" + password)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString(expected)));
	}

	@Test
	public void testLoginWithWrongPassword() throws Exception {

		String expected = "用户名或密码错误";
		String username = "测试错误用户名";
		String password = "测试错误密码";

		log.info("\n\n测试用例：{} 用户名：{} 密码：{}", expected, username, password);

		mockMvc.perform(get("/login?username=" + username + "&password=" + password)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString(expected)));
	}
}
