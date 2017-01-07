package cn.vpclub.spring.boot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;

@SpringBootApplication
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(
				SpringBootDemoApplication.class);

		application.addListeners(
				new ApplicationPidFileWriter("app.pid"));
		application.run(args);
	}
}
