package itu.mbds.transversal.data;


import itu.mbds.transversal.entity.Role;
import itu.mbds.transversal.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepo.findByAuthority("ADMIN") == null) {
            Role adminRole = new Role();
            adminRole.setAuthority("ADMIN");
            roleRepo.save(adminRole);
        }
        if (roleRepo.findByAuthority("USER") == null) {
            Role userRole = new Role();
            userRole.setAuthority("USER");
            roleRepo.save(userRole);
        }
    }
}