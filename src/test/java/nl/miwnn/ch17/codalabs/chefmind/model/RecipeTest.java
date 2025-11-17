package nl.miwnn.ch17.codalabs.chefmind.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        List<Category> expectedCategories = new ArrayList<>();

        Recipe recipe = new Recipe();

        recipe.setCategories(new ArrayList<>());

        // Act
        List<Category> categories = recipe.getCategories();

        // Assert
        assertEquals(expectedCategories, categories);
    }

    @Test
    @DisplayName("test total time based on prep time and cooking time")
    void testTotalTimeBasedOnPrepTimeAndCookingTime() {
        //Arrange
        Integer expectedTotalTime = 10;

        Recipe recipe = new Recipe();
        recipe.setPrepTime(6);
        recipe.setCookingTime(4);

        //Act
        Integer actualTotalTime = recipe.calculateTotalTime();

        //Assert
        assertEquals(expectedTotalTime, actualTotalTime);
    }
}
