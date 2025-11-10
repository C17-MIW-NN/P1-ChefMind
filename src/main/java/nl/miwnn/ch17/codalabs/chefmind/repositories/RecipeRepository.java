package nl.miwnn.ch17.codalabs.chefmind.repositories;

import nl.miwnn.ch17.codalabs.chefmind.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Assib Pajman
 *
 */
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
