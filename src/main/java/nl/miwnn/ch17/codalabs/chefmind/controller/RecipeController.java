package nl.miwnn.ch17.codalabs.chefmind.controller;

import nl.miwnn.ch17.codalabs.chefmind.model.Recipe;
import nl.miwnn.ch17.codalabs.chefmind.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Assib Pajman
 *
 */
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeRepository recipeRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/all")
    private String showRecipeOverview(Model datamodel, Recipe recipe) {
        datamodel.addAttribute("recipes", recipeRepository.findAll());
        datamodel.addAttribute("newRecipe", new Recipe());

        return "recipeOverview";
    }

    @GetMapping("/add")
    public String showRecipeForm(Model datamodel, Recipe recipe) {
        datamodel.addAttribute("newRecipe", new Recipe());

        return "recipeForm";
    }
}
