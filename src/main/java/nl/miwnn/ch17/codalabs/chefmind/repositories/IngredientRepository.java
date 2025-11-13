package nl.miwnn.ch17.codalabs.chefmind.repositories;

import nl.miwnn.ch17.codalabs.chefmind.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Nelleke Jansen
 * Repository of ingredients.
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByIngredientName(String ingredientName);
}
