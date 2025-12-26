package com.example.accelerator.config;

import com.example.accelerator.domain.entity.User;
import com.example.accelerator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // For Seeded Admins :
    @Override
    public void run(String... args) {

        seedAdmin(
                "superadmin@accelerator.com",
                "Super Admin",
                "admin123"
        );

        seedAdmin(
                "admin@accelerator.com",
                "Admin",
                "admin123"
        );
    }

    private void seedAdmin(
            String email,
            String name,
            String password
    ) {

        if (userRepository.findByEmail(email).isPresent()) {
            System.out.println(" already exists: " + email);
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        System.out.println("Seeded Admin " + ": " + email);
    }

}