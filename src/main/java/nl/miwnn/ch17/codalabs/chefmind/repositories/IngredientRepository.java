package nl.miwnn.ch17.codalabs.chefmind.repositories;

import nl.miwnn.ch17.codalabs.chefmind.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nelleke Jansen
 * Repository of ingredients.
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
