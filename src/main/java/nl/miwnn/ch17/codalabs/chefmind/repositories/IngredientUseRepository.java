package nl.miwnn.ch17.codalabs.chefmind.repositories;

import nl.miwnn.ch17.codalabs.chefmind.model.IngredientUse;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nelleke Jansen
 * Repository of uses of ingredients in recipes.
 */
public interface IngredientUseRepository extends JpaRepository<IngredientUse, Long> {
}
