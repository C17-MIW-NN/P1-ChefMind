package nl.miwnn.ch17.codalabs.chefmind.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Nelleke Jansen
 * Test methods from the Recipe class
 */
public class RecipeTest {

    @Test
    @DisplayName("test calculateTotalKcalPerServing() throws exception when serving size is 0")
    void testCalculateTotalKcalPerServingThrowsExceptionWhenServingSizeIsZero() {
        String expectedException = "Serving size must be greater than 0";

        Recipe recipe = new Recipe();
        recipe.setServingSize(0);

        addIngredientToRecipe(100, 100, recipe);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                recipe::calculateTotalKcalPerServing);

        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    @DisplayName("test calculateTotalKcalPerServing() when serving size is 1")
    void testCalculateTotalKcalPerServingWhenServingSizeIsOne() {
        Long expectedTotalKcalPerServing = 100L;

        Recipe recipe = new Recipe();
        recipe.setServingSize(1);

        addIngredientToRecipe(100, 100, recipe);

        Long totalKcalPerServing = recipe.calculateTotalKcalPerServing();

        assertEquals(expectedTotalKcalPerServing, totalKcalPerServing);
    }

    @Test
    @DisplayName("test calculateTotalKcalPerServing() when serving size is 2")
    void testCalculateTotalKcalPerServingWhenServingSizeIsTwo() {
        Long expectedTotalKcalPerServing = 50L;

        Recipe recipe = new Recipe();
        recipe.setServingSize(2);

        addIngredientToRecipe(100, 100, recipe);

        Long totalKcalPerServing = recipe.calculateTotalKcalPerServing();

        assertEquals(expectedTotalKcalPerServing, totalKcalPerServing);
    }

    @Test
    @DisplayName("test calculateTotalKcalPerServing() when serving size is 1000")
    void testCalculateTotalKcalPerServingWhenServingSizeIsOneThousand() {
        Long expectedTotalKcalPerServing = 1L;

        Recipe recipe = new Recipe();
        recipe.setServingSize(1000);

        addIngredientToRecipe(100, 1000, recipe);

        Long totalKcalPerServing = recipe.calculateTotalKcalPerServing();

        assertEquals(expectedTotalKcalPerServing, totalKcalPerServing);
    }

    @Test
    @DisplayName("test calculateTotalKcalPerServing() when total kcal is rounded down")
    void testCalculateTotalKcalPerServingWhenTotalKcalIsRoundedDown() {
        Long expectedTotalKcalPerServing = 33L;

        Recipe recipe = new Recipe();
        recipe.setServingSize(3);

        addIngredientToRecipe(100, 100, recipe);

        Long totalKcalPerServing = recipe.calculateTotalKcalPerServing();

        assertEquals(expectedTotalKcalPerServing, totalKcalPerServing);
    }

    @Test
    @DisplayName("test calculateTotalKcalPerServing() when total kcal is rounded up")
    void testCalculateTotalKcalPerServingWhenTotalKcalIsRoundedUp() {
        Long expectedTotalKcalPerServing = 67L;

        Recipe recipe = new Recipe();
        recipe.setServingSize(3);

        addIngredientToRecipe(100, 200, recipe);

        Long totalKcalPerServing = recipe.calculateTotalKcalPerServing();

        assertEquals(expectedTotalKcalPerServing, totalKcalPerServing);
    }

    @Test
    @DisplayName("test calculateTotalKcalPerServing() when no ingredients are used")
    void testCalculateTotalKcalPerServingWhenNoIngredientsAreUsed() {
        Long expectedTotalKcalPerServing = 0L;

        Recipe recipe = new Recipe();
        recipe.setServingSize(2);

        List<IngredientUse> ingredientUses = new ArrayList<>();
        recipe.setIngredientUses(ingredientUses);

        Long totalKcalPerServing = recipe.calculateTotalKcalPerServing();

        assertEquals(expectedTotalKcalPerServing, totalKcalPerServing);
    }

    @Test
    @DisplayName("test calculateTotalKcalPerServing() when multiple ingredients are used")
    void testCalculateTotalKcalPerServingWhenMultipleIngredientsAreUsed() {
        Long expectedTotalKcalPerServing = 500L;

        Recipe recipe = new Recipe();
        recipe.setServingSize(2);

        List<IngredientUse> ingredientUses = new ArrayList<>();
        addIngredientUseToList(100, 100, ingredientUses);
        addIngredientUseToList(200, 200, ingredientUses);
        addIngredientUseToList(500, 100, ingredientUses);
        recipe.setIngredientUses(ingredientUses);

        Long totalKcalPerServing = recipe.calculateTotalKcalPerServing();

        assertEquals(expectedTotalKcalPerServing, totalKcalPerServing);
    }

    private static void addIngredientToRecipe(Integer kcalPer100g, Integer quantityInGrams, Recipe recipe) {
        List<IngredientUse> ingredientUses = new ArrayList<>();
        addIngredientUseToList(kcalPer100g, quantityInGrams, ingredientUses);
        recipe.setIngredientUses(ingredientUses);
    }

    private static void addIngredientUseToList(Integer kcalPer100g, Integer quantityInGrams,
                                               List<IngredientUse> ingredientUses) {
        Ingredient ingredient = new Ingredient();
        ingredient.setKcalPer100g(kcalPer100g);

        IngredientUse ingredientUse = new IngredientUse();
        ingredientUse.setIngredient(ingredient);
        ingredientUse.setQuantityInGrams(quantityInGrams);

        ingredientUses.add(ingredientUse);
    }

    //TODO complete test unit for method calculateTotalTime
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
