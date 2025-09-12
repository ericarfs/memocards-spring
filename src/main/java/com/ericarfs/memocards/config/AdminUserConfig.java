package com.ericarfs.memocards.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.ericarfs.memocards.entity.User;
import com.ericarfs.memocards.entity.enums.Role;
import com.ericarfs.memocards.repository.UserRepository;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.password:admin}")
    private String adminPassword;

    public AdminUserConfig(UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var userAdmin = userRepository.findByUsername(adminUsername);

        userAdmin.ifPresentOrElse(
                user -> {
                },
                () -> {
                    var user = new User();
                    user.setUsername(adminUsername);
                    user.setPassword(passwordEncoder.encode(adminPassword));
                    user.setRole(Role.ADMIN);
                    userRepository.save(user);
                });
    }
}