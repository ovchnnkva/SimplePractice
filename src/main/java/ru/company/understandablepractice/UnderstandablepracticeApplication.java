package ru.company.understandablepractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.UserCredentials;
import ru.company.understandablepractice.service.UserService;

@SpringBootApplication
public class UnderstandablepracticeApplication {

	@Autowired
	private UserService service;

	public static void main(String[] args) {
		SpringApplication.run(UnderstandablepracticeApplication.class, args);
	}

	@Bean
	public ApplicationRunner CommandLineRunnerBean() {
		return (args) -> {
			User user = new User();
			user.setId(1);
			UserCredentials userCredentials = new UserCredentials();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			userCredentials.setPassword(encoder.encode("pass"));
			userCredentials.setUsername("user");
			user.setUserCredentials(userCredentials);
			userCredentials.setUser(user);

//			service.create(user);
		};
	}

}
