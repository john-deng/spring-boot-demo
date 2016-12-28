package cn.vpclub.spring.boot.demo;

import cn.vpclub.spring.boot.demo.service.UserService;
import cn.vpclub.spring.boot.demo.web.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	private void loginTestCase(String expected, String username, String password) throws Exception {
		given(userService.login("johnd", "123456"))
			.willReturn("登录成功");

		mockMvc.perform(get("/login?username=" + username + "&password=" + password)
			.accept(MediaType.TEXT_PLAIN))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString(expected)));
	}

	@Test
	public void testLogin() throws Exception {
		given(userService.login("johnd", "123456"))
				.willReturn("登录成功");

		mockMvc.perform(get("/login?username=johnd&password=123456")
				.accept(MediaType.TEXT_PLAIN))

				.andExpect(status().isOk())
				.andExpect(content().string(containsString("登录成功")));
	}
	@Test
	public void testLogin2() throws Exception {
		given(userService.login("johnd", "dafdaf"))
				.willReturn("用户名或密码错误");

		mockMvc.perform(get("/login?username=johnd&password=dafdaf")
				.accept(MediaType.TEXT_PLAIN))

				.andExpect(status().isOk())
				.andExpect(content().string(containsString("用户名或密码错误")));
	}

}
