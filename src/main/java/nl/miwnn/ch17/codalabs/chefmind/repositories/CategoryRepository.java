package nl.miwnn.ch17.codalabs.chefmind.repositories;

import nl.miwnn.ch17.codalabs.chefmind.model.Category;
import nl.miwnn.ch17.codalabs.chefmind.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Harm van der Weide
 * Purpose for the class
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(String categoryName);
}
