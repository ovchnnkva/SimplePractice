package ru.company.understandablepractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ru.company.understandablepractice.model.Role;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.UserCredentials;
import ru.company.understandablepractice.security.services.RoleService;
import ru.company.understandablepractice.service.UserService;

import java.util.Set;

@SpringBootApplication
public class UnderstandablepracticeApplication {

	@Autowired
	private UserService service;

	@Autowired
	private RoleService roleService;

	public static void main(String[] args) {
		SpringApplication.run(UnderstandablepracticeApplication.class, args);
	}

	@Bean
	public ApplicationRunner CommandLineRunnerBean() {
		return (args) -> {
			Role userRole = roleService.saveRole(new Role(1,"ROLE_USER"));
			roleService.saveRole(new Role(2, "ROLE_ADMIN"));
			roleService.saveRole(new Role(3, "ROLE_CUSTOMER"));
			roleService.saveRole(new Role(4, "ROLE_CHILD"));
			roleService.saveRole(new Role(5, "ROLE_PAIR"));

			User user = new User();
			user.setId(1);
			UserCredentials userCredentials = new UserCredentials();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			userCredentials.setPassword(encoder.encode("pass"));
			userCredentials.setUsername("anna");
			userCredentials.setRoles(Set.of(userRole));
			user.setUserCredentials(userCredentials);
			userCredentials.setUser(user);

//			service.create(user);

		};
	}

}
