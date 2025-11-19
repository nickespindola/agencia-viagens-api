package com.agencia.viagens.config;

import com.agencia.viagens.model.Usuario;
import com.agencia.viagens.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario("admin", passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of("ADMIN"));
            usuarioRepository.save(admin);
            
            Usuario user = new Usuario("user", passwordEncoder.encode("user123"));
            user.setRoles(Set.of("USER"));
            usuarioRepository.save(user);
            
            System.out.println("✅ Usuários iniciais criados:");
            System.out.println("   Admin - username: admin, password: admin123");
            System.out.println("   User - username: user, password: user123");
        }
    }
}
