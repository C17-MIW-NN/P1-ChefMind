package nl.miwnn.ch17.codalabs.chefmind.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Nelleke Jansen
 * Test methods from the Recipe class
 */
public class RecipeTest {

    @Test
    @DisplayName("test getter of categories of recipe when no categories are attached")
    void testGetterCategoriesOfRecipe() {

        // Arrange
        Set<Category> expectedCategories = new HashSet<Category>();

        Recipe recipe = new Recipe();

        recipe.setCategories(new HashSet<>());

        // Act
        Set<Category> categories = recipe.getCategories();

        // Assert
        assertEquals(expectedCategories, categories);
    }
}
