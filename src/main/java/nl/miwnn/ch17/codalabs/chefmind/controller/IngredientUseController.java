package nl.miwnn.ch17.codalabs.chefmind.controller;

import nl.miwnn.ch17.codalabs.chefmind.model.IngredientUse;
import nl.miwnn.ch17.codalabs.chefmind.repositories.IngredientRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.IngredientUseRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Nelleke Jansen
 * Handles requests regarding the use of ingredients in recipes.
 */
@Controller
@RequestMapping("/ingredientuse")
public class IngredientUseController {

    private final IngredientUseRepository ingredientUseRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    public IngredientUseController(IngredientUseRepository ingredientUseRepository, IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.ingredientUseRepository = ingredientUseRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/add")
    public String showIngredientUseForm(Model datamodel) {
        return showIngredientUseForm(datamodel, new IngredientUse());
    }

    private String showIngredientUseForm(Model datamodel, IngredientUse ingredientUse) {
        datamodel.addAttribute("formIngredientUse", ingredientUse);
        datamodel.addAttribute("allRecipes", recipeRepository.findAll());
        datamodel.addAttribute("allIngredients", ingredientRepository.findAll());

        return "ingredientUseForm";
    }

    @PostMapping("/save")
    public String saveOrUpdateIngredientUse(@ModelAttribute("formIngredientUse") IngredientUse ingredientUse,
                                            BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/recipe/all";
        }

        ingredientUseRepository.save(ingredientUse);
        return "redirect:/recipe/detail/" + ingredientUse.getRecipe().getName();
    }

}
