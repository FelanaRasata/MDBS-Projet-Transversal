package itu.mbds.transversal.service.user;

import itu.mbds.transversal.entity.User;
import itu.mbds.transversal.repository.UserRepo;
import itu.mbds.transversal.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class AuthService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepo userRepo;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.debug("Entering in loadUserByUsername Method...");
        User user = userRepo.findByUsernameIgnoreCase(username);
        if (user == null) {
            logger.error("Username not found: " + username);
            throw new UsernameNotFoundException("Utilisateur " + username + " est introuvable");
        }
        logger.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user);
    }


}