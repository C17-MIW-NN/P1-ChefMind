package nl.miwnn.ch17.codalabs.chefmind.model;

import nl.miwnn.ch17.codalabs.chefmind.controller.InitializeController;
import nl.miwnn.ch17.codalabs.chefmind.repositories.CategoryRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.IngredientRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.IngredientUseRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.RecipeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Assib Pajman
 *
 */
public class InitializeControllerTest {
    CategoryRepository categoryRepository;
    RecipeRepository recipeRepository;
    IngredientRepository ingredientRepository;
    IngredientUseRepository ingredientUseRepository;

    @Test
    @DisplayName("Test turning a string into a list of strings")
    void testTurningAStringIntoAListOfStrings() {
        String stringToBeSplit = "Mix flour, baking powder, sugar, milk, egg, and butter into a smooth batter.;" +
        "Cook on a greased pan until bubbles form.;Flip and cook until golden.;Serve with syrup.";

        List<String> expectedlist = new ArrayList<>();
        expectedlist.add("Mix flour, baking powder, sugar, milk, egg, and butter into a smooth batter.");
        expectedlist.add("Cook on a greased pan until bubbles form.");
        expectedlist.add("Flip and cook until golden.");
        expectedlist.add("Serve with syrup.");

        InitializeController controller = new InitializeController(
                categoryRepository, recipeRepository, ingredientRepository, ingredientUseRepository);

        List<String> actualList = controller.turnStringIntoListOfStrings(stringToBeSplit);

        assertEquals(expectedlist, actualList);
    }

}
