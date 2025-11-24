package nl.miwnn.ch17.codalabs.chefmind.controller;

import nl.miwnn.ch17.codalabs.chefmind.model.*;
import nl.miwnn.ch17.codalabs.chefmind.repositories.CategoryRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.IngredientRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.IngredientUseRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.RecipeRepository;
import nl.miwnn.ch17.codalabs.chefmind.service.ChefMindUserService;
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
    private final ChefMindUserService chefMindUserService;

    public InitializeController(CategoryRepository categoryRepository,
                                RecipeRepository recipeRepository,
                                IngredientRepository ingredientRepository,
                                IngredientUseRepository ingredientUseRepository, ChefMindUserService chefMindUserService) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientUseRepository = ingredientUseRepository;
        this.chefMindUserService = chefMindUserService;
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent) {
        if (recipeRepository.count() == 0) {
            initializeDB();
        }
    }

    private void initializeDB() {
        makeUser("John", "ilovepizza");
        makeUser("Jane", "cookies123");

        Category breakfast = makeCategory("Breakfast");
        Category lunch = makeCategory("Lunch");
        Category dinner = makeCategory("Dinner");
        Category desserts = makeCategory("Desserts");
        Category salads = makeCategory("Salads");
        Category soups = makeCategory("Soups");
        Category cookies = makeCategory("Cookies");
        Category vegetarian = makeCategory("Vegetarian");
        Category glutenfree = makeCategory("Gluten-free");
        Category italian = makeCategory("Italian");
        Category greek = makeCategory("Greek");
        Category korean = makeCategory("Korean");
        Category favourites = makeCategory("Favourites");

        Ingredient salt = makeIngredient("salt", 0);
        Ingredient pepper = makeIngredient("pepper", 255);
        Ingredient oliveOil = makeIngredient("olive oil", 884);
        Ingredient bread = makeIngredient("bread", 265);
        Ingredient cheese = makeIngredient("Gouda cheese", 356);
        Ingredient onion = makeIngredient("onion", 40);
        Ingredient garlic = makeIngredient("garlic", 149);
        Ingredient tomato = makeIngredient("tomato", 18);
        Ingredient tomatoPaste = makeIngredient("tomato paste", 82);
        Ingredient beef = makeIngredient("beef", 250);
        Ingredient spaghetti = makeIngredient("spaghetti", 371);
        Ingredient basil = makeIngredient("basil", 23);
        Ingredient oregano = makeIngredient("oregano", 306);
        Ingredient parmesan = makeIngredient("Parmesan cheese", 431);
        Ingredient carrot = makeIngredient("carrot", 41);
        Ingredient celery = makeIngredient("celery", 16);
        Ingredient broth = makeIngredient("vegetable broth", 25);
        Ingredient flour = makeIngredient("flour", 364);
        Ingredient milk = makeIngredient("milk", 61);
        Ingredient sugar = makeIngredient("sugar", 387);
        Ingredient bakingPowder = makeIngredient("baking powder", 53);
        Ingredient butter = makeIngredient("Butter", 717);
        Ingredient egg = makeIngredient("egg", 143);
        Ingredient chocolateChips = makeIngredient("chocolate chips", 479);

        Recipe cheeseSandwich = makeRecipe("Cheese Sandwich", 1, 5, 0,
                "Cut slices of cheese with a cheese slicer.;Place the cheese on a piece of bread.;" +
                        "Top off with another piece of bread. Voilà!",
                "https://californiaavocado.com/wp-content/uploads/2023/04/" +
                        "AvoBaconGrilledCheese_0011-scaled-e1682914545487.jpg", lunch);
        makeIngredientUse(cheeseSandwich, cheese, "2 slices", 100);
        makeIngredientUse(cheeseSandwich, bread, "2 pieces", 50);

        Recipe spaghettiBolognese = makeRecipe("Spaghetti Bolognese", 4, 10, 25,
                "Sauté onion, garlic, and minced beef until browned. " +
                        "Add tomato paste, crushed tomatoes, herbs, salt, and pepper.;Simmer 30 minutes.;" +
                        "Serve over cooked spaghetti and top with Parmesan.",
                "https://supervalu.ie/image/var/files/real-food/recipes/Uploaded-2020/" +
                        "spaghetti-bolognese-recipe.jpg", dinner, italian);
        makeIngredientUse(spaghettiBolognese, onion, "1", 100);
        makeIngredientUse(spaghettiBolognese, garlic, "2 cloves", 10);
        makeIngredientUse(spaghettiBolognese, salt, "to taste", 2);
        makeIngredientUse(spaghettiBolognese, pepper, "dash", 2);
        makeIngredientUse(spaghettiBolognese, spaghetti, "4 portions", 350);
        makeIngredientUse(spaghettiBolognese, tomato, "5", 400);
        makeIngredientUse(spaghettiBolognese, tomatoPaste, "2 table spoons", 10);

        Recipe vegetableSoup = makeRecipe("Vegetable Soup", 6, 10, 30,
                "Sauté onion, carrot, and celery.;Add diced tomatoes, broth, and seasonings.;" +
                        "Simmer until vegetables are tender.;Adjust salt and pepper before serving.",
                "https://www.allrecipes.com/thmb/wYELcGueAb7YS20dQ95t22T1CDs=/" +
                        "0x512/filters:no_upscale():max_bytes(150000):strip_icc()/" +
                        "13338-quick-and-easy-vegetable-soup-DDMFS-4x3-402702f59e7a41519515cecccaba1b80.jpg",
                lunch, soups, vegetarian);
        makeIngredientUse(vegetableSoup, onion, "1", 100);
        makeIngredientUse(vegetableSoup, carrot, "3", 400);
        makeIngredientUse(vegetableSoup, celery, "4 stalks", 400);
        makeIngredientUse(vegetableSoup, broth, "1 liter", 1000);

        Recipe pancakes = makeRecipe("Pancakes", 4, 5, 15,
                "Mix flour, baking powder, sugar, milk, egg, and butter into a smooth batter.;" +
                        "Cook on a greased pan until bubbles form.;Flip and cook until golden.;Serve with syrup.",
                "https://assets.tmecosys.com/image/upload/t_web_rdp_recipe_584x480_1_5x/img/recipe/ras/Assets/" +
                        "bdbc3ce86f919fb2dcf45204579802a8/Derivates/596b2aed41a777a097e3dd5917f8381d557e0ee7.jpg",
                breakfast);
        makeIngredientUse(pancakes, flour, "150g", 150);
        makeIngredientUse(pancakes, bakingPowder, "10g", 10);
        makeIngredientUse(pancakes, sugar, "25g", 25);
        makeIngredientUse(pancakes, egg, "1", 100);

        Recipe chocolateChipCookies = makeRecipe("Chocolate Chip Cookies", 24,
                10, 12,
                "Mix butter, sugar, and egg.;Add flour, baking powder, and chocolate chips.;" +
                        "Drop spoonfuls on a tray and bake at 175°C for 10–12 minutes.", "https://www.allrecipes" +
                        ".com/thmb/dNzzgeEyacuH-RIfMI4PjWFODBM=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc" +
                        "()/AR-9996-chewy-peanut-butter-chocolate-chip-cookies-" +
                        "ddmfs-4x3-614164ea044a4845b3cca7e725ecf7bd.jpg", cookies, favourites);
        makeIngredientUse(chocolateChipCookies, chocolateChips, "150 grams", 150);
    }

    private ChefMindUser makeUser(String username, String password) {
        ChefMindUser user = new ChefMindUser();

        user.setUsername(username);
        user.setPassword(password);

        chefMindUserService.saveUser(user);
        return user;
    }

    private Category makeCategory(String categoryName) {
        Category category = new Category();

        category.setCategoryName(categoryName);
        categoryRepository.save(category);

        return category;
    }

    private Ingredient makeIngredient(String ingredientName, Integer kcalPer100g) {
        Ingredient ingredient = new Ingredient();

        ingredient.setIngredientName(ingredientName);
        ingredient.setKcalPer100g(kcalPer100g);

        ingredientRepository.save(ingredient);

        return ingredient;
    }

    private Recipe makeRecipe(String name, Integer servingSize, Integer prepTime, Integer cookingTime,
                              String instructions, String image, Category ... categories) {
        String[] parts = instructions.split(";");
        List<String> instructionList = new ArrayList<>(Arrays.asList(parts));

        Recipe recipe = new Recipe();

        recipe.setName(name);
        recipe.setServingSize(servingSize);
        recipe.setPrepTime(prepTime);
        recipe.setCookingTime(cookingTime);
        recipe.setInstructions(instructionList);
        recipe.setImage(image);

        List<Category> categoryList= new ArrayList<>(Arrays.asList(categories));
        recipe.setCategories(categoryList);

        recipeRepository.save(recipe);

        return recipe;
    }

    private IngredientUse makeIngredientUse(Recipe recipe, Ingredient ingredient, String amount,
                                            Integer quantityInGrams) {
        IngredientUse ingredientUse = new IngredientUse();

        ingredientUse.setRecipe(recipe);
        ingredientUse.setIngredient(ingredient);
        ingredientUse.setAmount(amount);
        ingredientUse.setQuantityInGrams(quantityInGrams);

        ingredientUseRepository.save(ingredientUse);

        return ingredientUse;
    }

    public List<String> turnStringIntoListOfStrings(String string) {
        String[] parts = string.split(";");

        return new ArrayList<>(Arrays.asList(parts));
    }
}
