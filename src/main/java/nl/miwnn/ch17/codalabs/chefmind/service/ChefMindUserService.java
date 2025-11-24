package nl.miwnn.ch17.codalabs.chefmind.service;

import nl.miwnn.ch17.codalabs.chefmind.model.ChefMindUser;
import nl.miwnn.ch17.codalabs.chefmind.repositories.ChefMindUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Nelleke Jansen
 */

@Service
public class ChefMindUserService implements UserDetailsService {

    private final ChefMindUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ChefMindUserService(ChefMindUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found."));
    }

    public void saveUser(ChefMindUser chefMindUser) {
        chefMindUser.setPassword(passwordEncoder.encode(chefMindUser.getPassword()));
        repository.save(chefMindUser);
    }
}
