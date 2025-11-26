package nl.miwnn.ch17.codalabs.chefmind.repositories;

import nl.miwnn.ch17.codalabs.chefmind.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByName(String name);
    List<Recipe> findByCategories_CategoryId(Long categoryId);
    List<Recipe> findByNameContainingIgnoreCase(String name);
    List<Recipe> findDistinctByIngredientUses_Ingredient_IngredientNameIgnoreCase(String ingredientName);
    List<Recipe> findDistinctByCategories_CategoryNameIgnoreCase(String categoryName);
}
