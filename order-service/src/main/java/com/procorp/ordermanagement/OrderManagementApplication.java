package com.procorp.ordermanagement;


import com.procorp.ordermanagement.entities.Product;
import com.procorp.ordermanagement.entities.User;
import com.procorp.ordermanagement.repositories.UserRepository;
import com.procorp.ordermanagement.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class OrderManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementApplication.class, args);
	}
/*
	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
				User user = new User(name, name.toLowerCase() + "@domain.com");
				userRepository.save(user);
			});
			userRepository.findAll().forEach(System.out::println);
		};
	}

	@Bean
	CommandLineRunner runner(ProductService productService) {
		return args -> {
			productService.save(new Product(1L, "TV Set", 300.00,10.0, "http://placehold.it/200x100","Jeans","Male"));
			productService.save(new Product(2L, "Game Console", 200.00,10.0, "http://placehold.it/200x100","Jeans","Female"));
			productService.save(new Product(3L, "Sofa", 100.00, 10.0,"http://placehold.it/200x100","tshirts","Male"));
			productService.save(new Product(4L, "Icecream", 5.00, 10.0,"http://placehold.it/200x100","Jeans","Female"));
			productService.save(new Product(5L, "Beer", 3.00, 10.0,"http://placehold.it/200x100","Chudidhars","Female"));
			productService.save(new Product(6L, "Phone", 500.00, 10.0,"http://placehold.it/200x100","shirts","Kids"));
			productService.save(new Product(7L, "Watch", 30.00, 10.0,"http://placehold.it/200x100","Saree","Female"));
		};
	}*/
}
