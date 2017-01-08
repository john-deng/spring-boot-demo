package cn.vpclub.spring.boot.demo;

import cn.vpclub.common.tools.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

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

	public String post(String uri,String jsonParam) throws Exception{
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonParam))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		Assert.assertNotNull(content);
		return content;
	}

	@Test(dependsOnMethods = "testLoginWithWrongPassword")
	public void testLogin() throws Exception {

		String expected = "登录成功";
		String username = "johnd";
		String password = "123456";

		String uri = "/login";
		Map<String, Object> map = new HashMap<>();
		map.put("userName",username);
		map.put("password",password);

		String inputJson = JsonUtil.objectToJson(map);
		log.info("paramMap: " + inputJson);

		String content = post(uri,inputJson);
		Assert.assertNotNull(content);
		Assert.assertTrue(!content.equals(""));
		// we should verify message equals to expected value
		log.info("result: " + content);
	}

	@Test
	public void testLoginWithWrongPassword() throws Exception {

		String expected = "用户名或密码错误";
		String username = "测试错误用户名";
		String password = "测试错误密码";

		String uri = "/login";
		Map<String, Object> map = new HashMap<>();
		map.put("userName",username);
		map.put("password",password);

		String inputJson = JsonUtil.objectToJson(map);
		log.info("paramMap: " + inputJson);

		String content = post(uri,inputJson);
		Assert.assertNotNull(content);
		Assert.assertTrue(!content.equals(""));
		// we should verify message equals to expected value
		log.info("result: " + content);
	}
}
