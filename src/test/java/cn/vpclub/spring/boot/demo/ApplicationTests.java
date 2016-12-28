package cn.vpclub.spring.boot.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootDemoApplication.class}, webEnvironment = DEFINED_PORT)
@Slf4j
public class ApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setupMockMvc() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	private void loginTestCase(String expected, String username, String password) throws Exception {

		log.info("\n\n{} {} {}", expected, username, password);

		mockMvc.perform(get("/login?username=" + username + "&password=" + password)
			.accept(MediaType.TEXT_PLAIN))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString(expected)));
	}

	@Test
	public void testLogin() throws Exception {
		loginTestCase("登录成功", "johnd", "123456");
		loginTestCase("用户名或密码错误", "johnd", "a;djfagj");
		loginTestCase("用户名或密码错误", "dakjda", "123456");
	}

}
