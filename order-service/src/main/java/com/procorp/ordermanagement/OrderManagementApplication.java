package com.procorp.ordermanagement;


import com.procorp.ordermanagement.dto.CategoryDto;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.Product;
import com.procorp.ordermanagement.entities.User;
import com.procorp.ordermanagement.repositories.UserRepository;
import com.procorp.ordermanagement.service.CategoryService;
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
	CommandLineRunner runner(ProductService productService, CategoryService categoryService) {
		return args -> {
			CategoryDto dto=new CategoryDto();
			dto.setCategoryType("Grocery");
			dto.setDescription("this category used for kitech");
			Category category= categoryService.create(dto);
			productService.save(new Product(1L, "TV Set", 300.00, "http://placehold.it/200x100","Male","KG",category));
			productService.save(new Product(2L, "Game Console", 200.00, "http://placehold.it/200x100","Female","KG",category));
			productService.save(new Product(3L, "Sofa", 100.00, "http://placehold.it/200x100","Male","KG",category));
			productService.save(new Product(4L, "Icecream", 5.00, "http://placehold.it/200x100","Female","KG",category));
			productService.save(new Product(5L, "Beer", 3.00, "http://placehold.it/200x100","Female","KG",category));
			productService.save(new Product(6L, "Phone", 500.00, "http://placehold.it/200x100","Kids","KG",category));
			productService.save(new Product(7L, "Watch", 30.00, "http://placehold.it/200x100","Female","KG",category));
		};
	}
}
