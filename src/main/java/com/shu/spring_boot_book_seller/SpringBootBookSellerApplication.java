package com.shu.spring_boot_book_seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import com.shu.spring_boot_book_seller.model.Role;
import com.shu.spring_boot_book_seller.model.User;
import com.shu.spring_boot_book_seller.repository.IUserRepository;

import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
@PropertySource("classpath:application-${spring.profiles.active:default}.properties")
public class SpringBootBookSellerApplication {



	public static void main(String[] args) {
		SpringApplication.run(SpringBootBookSellerApplication.class, args);
	}

    @Bean
    CommandLineRunner createAdmin(IUserRepository userRepository,
                                  PasswordEncoder passwordEncoder) {

        return args -> {

            System.out.println("========== CommandLineRunner Started ==========");

            try {

                if (userRepository.findByUsername("admin").isEmpty()) {

                    System.out.println("Admin does not exist. Creating...");

                    User admin = new User();
                    admin.setName("Administrator");
                    admin.setUsername("admin");
                    admin.setPassword(passwordEncoder.encode("admin123"));
                    admin.setRole(Role.ADMIN);
                    admin.setCreateTime(LocalDateTime.now());

                    userRepository.save(admin);

                    System.out.println("Admin created successfully.");

                } else {
                    System.out.println("Admin already exists.");
                }

            } catch (Exception e) {

                System.out.println("Exception while creating admin:");
                e.printStackTrace();
            }
        };
    }
}
