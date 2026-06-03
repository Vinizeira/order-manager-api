package dev.projectx.order_manager_api.config;

import dev.projectx.order_manager_api.model.Role;
import dev.projectx.order_manager_api.model.Usuario;
import dev.projectx.order_manager_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (adminEmail == null || adminEmail.isBlank() || adminPassword == null || adminPassword.isBlank()) {
            return;
        }

        String email = adminEmail.toLowerCase();

        if (usuarioRepository.findByEmail(email).isEmpty()) {
            Usuario admin = new Usuario(
                    "Administrador",
                    email,
                    passwordEncoder.encode(adminPassword),
                    Role.ROLE_ADMIN
            );

            usuarioRepository.save(admin);
        }
    }
}
