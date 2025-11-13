package nl.miwnn.ch17.codalabs.chefmind.controller;

import nl.miwnn.ch17.codalabs.chefmind.model.Category;
import nl.miwnn.ch17.codalabs.chefmind.model.Ingredient;
import nl.miwnn.ch17.codalabs.chefmind.model.IngredientUse;
import nl.miwnn.ch17.codalabs.chefmind.model.Recipe;
import nl.miwnn.ch17.codalabs.chefmind.repositories.CategoryRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.IngredientRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.IngredientUseRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.RecipeRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * @author Nelleke Jansen
 * Initializes the database with example data.
 */
@Controller
public class InitializeController {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientUseRepository ingredientUseRepository;

    public InitializeController(CategoryRepository categoryRepository,
                                RecipeRepository recipeRepository,
                                IngredientRepository ingredientRepository,
                                IngredientUseRepository ingredientUseRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientUseRepository = ingredientUseRepository;
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent) {
        if (recipeRepository.count() == 0) {
            initializeDB();
        }
    }

    private void initializeDB() {
        Category breakfast = makeCategory("breakfast");
        Category lunch = makeCategory("lunch");
        Category dinner = makeCategory("dinner");
        Category desserts = makeCategory("desserts");
        Category salads = makeCategory("salads");
        Category soups = makeCategory("soups");
        Category cookies = makeCategory("cookies");
        Category vegetarian = makeCategory("vegetarian");
        Category glutenfree = makeCategory("gluten-free");
        Category italian = makeCategory("Italian");
        Category greek = makeCategory("Greek");
        Category korean = makeCategory("Korean");
        Category favourites = makeCategory("favourites");

        Ingredient salt = makeIngredient("salt");
        Ingredient pepper = makeIngredient("pepper");
        Ingredient oliveOil = makeIngredient("olive oil");
        Ingredient bread = makeIngredient("bread");
        Ingredient cheese = makeIngredient("Gouda cheese");
        Ingredient onion = makeIngredient("onion");
        Ingredient garlic = makeIngredient("garlic");
        Ingredient tomato = makeIngredient("tomato");
        Ingredient tomatoPaste = makeIngredient("tomato paste");
        Ingredient beef = makeIngredient("beef");
        Ingredient spaghetti = makeIngredient("spaghetti");
        Ingredient basil = makeIngredient("basil");
        Ingredient oregano = makeIngredient("oregano");
        Ingredient parmesan = makeIngredient("Parmesan cheese");
        Ingredient carrot = makeIngredient("carrot");
        Ingredient celery = makeIngredient("celery");
        Ingredient broth = makeIngredient("vegetable broth");
        Ingredient flour = makeIngredient("flour");
        Ingredient milk = makeIngredient("milk");
        Ingredient sugar = makeIngredient("sugar");
        Ingredient bakingPowder = makeIngredient("baking powder");
        Ingredient butter = makeIngredient("butter");
        Ingredient egg = makeIngredient("egg");
        Ingredient chocolateChips = makeIngredient("chocolate chips");

        Recipe cheeseSandwich = makeRecipe("Cheese sandwich", 1, 5,
                "Cut slices of cheese with a cheese slicer. Place the cheese on a piece of bread. " +
                        "Top off with another piece of bread. Voilà!", lunch);
        makeIngredientUse(cheeseSandwich, cheese, "2 slices");
        makeIngredientUse(cheeseSandwich, bread, "2 pieces");

        Recipe spaghettiBolognese = makeRecipe("Spaghetti Bolognese", 4, 45,
                "Sauté onion, garlic, and minced beef until browned. " +
                        "Add tomato paste, crushed tomatoes, herbs, salt, and pepper. Simmer 30 minutes. " +
                        "Serve over cooked spaghetti and top with Parmesan.", dinner, italian);
        makeIngredientUse(spaghettiBolognese, onion, "1");
        makeIngredientUse(spaghettiBolognese, garlic, "2 cloves");
        makeIngredientUse(spaghettiBolognese, salt, "to taste");
        makeIngredientUse(spaghettiBolognese, pepper, "dash");
        makeIngredientUse(spaghettiBolognese, spaghetti, "4 portions");
        makeIngredientUse(spaghettiBolognese, tomato, "5");
        makeIngredientUse(spaghettiBolognese, tomatoPaste, "2 table spoons");

        Recipe vegetableSoup = makeRecipe("Vegetable soup", 6, 40,
                "Sauté onion, carrot, and celery. Add diced tomatoes, broth, and seasonings. " +
                        "Simmer until vegetables are tender. Adjust salt and pepper before serving.",
                lunch, soups, vegetarian);
        makeIngredientUse(vegetableSoup, onion, "1");
        makeIngredientUse(vegetableSoup, carrot, "3");
        makeIngredientUse(vegetableSoup, celery, "4 stalks");
        makeIngredientUse(vegetableSoup, broth, "1 liter");

        Recipe pancakes = makeRecipe("Pancakes", 4, 20,
                "Mix flour, baking powder, sugar, milk, egg, and butter into a smooth batter. " +
                        "Cook on a greased pan until bubbles form. Flip and cook until golden. Serve with syrup.",
                breakfast);
        makeIngredientUse(pancakes, flour, "150g");
        makeIngredientUse(pancakes, bakingPowder, "10g");
        makeIngredientUse(pancakes, sugar, "25g");
        makeIngredientUse(pancakes, egg, "1");

        Recipe chocolateChipCookies = makeRecipe("Chocolate chip cookies", 24, 25,
                "Mix butter, sugar, and egg. Add flour, baking powder, and chocolate chips. " +
                        "Drop spoonfuls on a tray and bake at 175°C for 10–12 minutes.", cookies, favourites);
        makeIngredientUse(chocolateChipCookies, chocolateChips, "150 grams");
    }

    private Category makeCategory(String categoryName) {
        Category category = new Category();

        category.setCategoryName(categoryName);
        categoryRepository.save(category);

        return category;
    }

    private Ingredient makeIngredient(String ingredientName) {
        Ingredient ingredient = new Ingredient();

        ingredient.setIngredientName(ingredientName);

        ingredientRepository.save(ingredient);

        return ingredient;
    }

    private Recipe makeRecipe(String name, Integer servingSize, Integer time,
                              String instructions, Category ... categories) {
        List<String> instructionList = new ArrayList<>();
        instructionList.add(instructions);
        Recipe recipe = new Recipe();

        recipe.setName(name);
        recipe.setServingSize(servingSize);
        recipe.setTime(time);
        recipe.setInstructions(instructionList);

        Set<Category> categorySet= new HashSet<>(Arrays.asList(categories));
        recipe.setCategories(categorySet);

        recipeRepository.save(recipe);

        return recipe;
    }

    private IngredientUse makeIngredientUse(Recipe recipe, Ingredient ingredient, String amount) {
        IngredientUse ingredientUse = new IngredientUse();

        ingredientUse.setRecipe(recipe);
        ingredientUse.setIngredient(ingredient);
        ingredientUse.setAmount(amount);

        ingredientUseRepository.save(ingredientUse);

        return ingredientUse;
    }
}
