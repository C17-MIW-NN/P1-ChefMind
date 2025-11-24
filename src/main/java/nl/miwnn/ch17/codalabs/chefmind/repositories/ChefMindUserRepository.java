package nl.miwnn.ch17.codalabs.chefmind.repositories;

import nl.miwnn.ch17.codalabs.chefmind.model.ChefMindUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * @author Nelleke Jansen
 */
public interface ChefMindUserRepository extends JpaRepository<ChefMindUser, Long> {
    Optional<ChefMindUser> findByUsername(String username);
}
