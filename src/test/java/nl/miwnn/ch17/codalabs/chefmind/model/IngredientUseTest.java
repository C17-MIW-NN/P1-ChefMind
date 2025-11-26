package nl.miwnn.ch17.codalabs.chefmind.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Assib Pajman
 * For testing the method that calculates the amount of Kcal of an IngredientUse in the IngredientUse class
 */
public class IngredientUseTest {

    @Test
    @DisplayName("Test normal calculation of Kcal of an IngredientUse")
    public void testTheCalculationOfTheAmountOfKcalOfAnIngredientUse() {
        Double expectedValue = 50.0;

        Ingredient ingredient = new Ingredient();
        IngredientUse ingredientUse = new IngredientUse();

        Integer caloriesPer100g = 100;
        Integer quantityIngrams = 50;

        ingredient.setKcalPer100g(caloriesPer100g);
        ingredientUse.setIngredient(ingredient);
        ingredientUse.setQuantityInGrams(quantityIngrams);

        Double actualValue = ingredientUse.calculateKcalIngredientUse();

        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("Test the calculation of the amount of Kcal of an IngredientUse when quantity is 0")
    public void testTheCalculationOfTheAmountOfKcalOfAnIngredientUseQuantityIsZero() {
        Double expectedValue = 0.0;

        Ingredient ingredient = new Ingredient();
        IngredientUse ingredientUse = new IngredientUse();

        Integer caloriesPer100g = 100;
        Integer quantityIngrams = 0;

        ingredient.setKcalPer100g(caloriesPer100g);
        ingredientUse.setIngredient(ingredient);
        ingredientUse.setQuantityInGrams(quantityIngrams);

        Double actualValue = ingredientUse.calculateKcalIngredientUse();

        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("Test the calculation of the amount of Kcal of an IngredientUse when calories per 100g is 0")
    public void testTheCalculationOfTheAmountOfKcalOfAnIngredientUseCaloriesPer100gIsZero() {
        Double expectedValue = 0.0;

        Ingredient ingredient = new Ingredient();
        IngredientUse ingredientUse = new IngredientUse();

        Integer caloriesPer100g = 0;
        Integer quantityIngrams = 50;

        ingredient.setKcalPer100g(caloriesPer100g);
        ingredientUse.setIngredient(ingredient);
        ingredientUse.setQuantityInGrams(quantityIngrams);

        Double actualValue = ingredientUse.calculateKcalIngredientUse();

        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("Test the calculation of the amount of Kcal in case of a negative caloriesPer100g")
    public void testTheCalculationOfTheAmountOfKcalOfAnIngredientUseWhenCaloriesPer100gNegative() {
        Ingredient ingredient = new Ingredient();
        IngredientUse ingredientUse = new IngredientUse();

        Integer caloriesPer100g = -1;
        Integer quantityIngrams = 50;

        ingredient.setKcalPer100g(caloriesPer100g);
        ingredientUse.setIngredient(ingredient);
        ingredientUse.setQuantityInGrams(quantityIngrams);

        assertThrows(IllegalArgumentException.class, ingredientUse::calculateKcalIngredientUse);

    }

    @Test
    @DisplayName("Test the calculation of the amount of Kcal in case of a negative quantityIngrams")
    public void testTheCalculationOfTheAmountOfKcalOfAnIngredientUseWhenQuantityInGramsNegative() {
        Ingredient ingredient = new Ingredient();
        IngredientUse ingredientUse = new IngredientUse();

        Integer caloriesPer100g = 100;
        Integer quantityIngrams = -1;

        ingredient.setKcalPer100g(caloriesPer100g);
        ingredientUse.setIngredient(ingredient);
        ingredientUse.setQuantityInGrams(quantityIngrams);

        assertThrows(IllegalArgumentException.class, ingredientUse::calculateKcalIngredientUse);
    }
}
