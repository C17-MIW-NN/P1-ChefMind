package nl.miwnn.ch17.codalabs.chefmind.controller;

import nl.miwnn.ch17.codalabs.chefmind.model.Ingredient;
import nl.miwnn.ch17.codalabs.chefmind.model.IngredientUse;
import nl.miwnn.ch17.codalabs.chefmind.model.Recipe;
import nl.miwnn.ch17.codalabs.chefmind.repositories.IngredientRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.IngredientUseRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.CategoryRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Assib Pajman
 *
 */
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientUseRepository ingredientUseRepository;
    private final CategoryRepository categoryRepository;

    public RecipeController(RecipeRepository recipeRepository, IngredientRepository ingredientRepository,
                            IngredientUseRepository ingredientUseRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientUseRepository = ingredientUseRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/all")
    public String showRecipeOverview(Model datamodel, Recipe recipe) {
        datamodel.addAttribute("recipes", recipeRepository.findAll());
        datamodel.addAttribute("formRecipe", new Recipe());

        return "recipeOverview";
    }

//    @PostMapping("/new")
//    public String saveNewRecipe(@ModelAttribute("formRecipe") Recipe recipeToBeSaved,
//                                BindingResult result,
//                                Model datamodel) {
//        Optional<Recipe> recipeWithSameName = recipeRepository.findByName(recipeToBeSaved.getName());
//
//        if (recipeWithSameName.isPresent() &&
//                !recipeWithSameName.get().getRecipeId().equals(recipeToBeSaved.getRecipeId())) {
//            result.addError(new FieldError("recipe",
//                    "name",
//                    "Recipe name already exists"));
//        }
//
//        if (result.hasErrors()) {
//            return showRecipeForm(datamodel, recipeToBeSaved);
//        }
//
//        recipeRepository.save(recipeToBeSaved);
//        return "redirect:/recipe/edit/" + recipeToBeSaved.getName();
//    }

    @GetMapping("/new")
    public String showNewRecipeForm(Model datamodel) {
        return showRecipeForm(datamodel, new Recipe());
    }

    @GetMapping("/edit/{name}")
    public String showEditRecipeForm(@PathVariable("name") String name, Model datamodel) {
        Optional<Recipe> optionalRecipe = recipeRepository.findByName(name);

        if (optionalRecipe.isPresent()) {
            return showRecipeForm(datamodel, optionalRecipe.get());
        }

        return "redirect:/recipe/all";
    }

    @PostMapping("/save")
    public String saveOrUpdateRecipe(@ModelAttribute("formRecipe") Recipe recipeToBeSaved,
                                     @RequestParam(value = "ingredientNames[]", required = false) List<String> ingredientNames,
                                     @RequestParam(value = "amounts[]", required = false) List<String> amounts,
                                     BindingResult result,
                                     Model datamodel) {
        Optional<Recipe> recipeWithSameName = recipeRepository.findByName(recipeToBeSaved.getName());

        if (recipeWithSameName.isPresent() &&
                !recipeWithSameName.get().getRecipeId().equals(recipeToBeSaved.getRecipeId())) {
            result.addError(new FieldError("recipe",
                    "name",
                    "Recipe name already exists"));
        }

        if (result.hasErrors()) {
            return showRecipeForm(datamodel, recipeToBeSaved);
        }

        List<IngredientUse> ingredientUses = new ArrayList<>();

        if (ingredientNames == null) {
            ingredientNames = new ArrayList<>();
        }

        for (int ingredientIndex = 0; ingredientIndex < ingredientNames.size(); ingredientIndex++) {
            String ingredientName = ingredientNames.get(ingredientIndex);
            String amount = amounts.size() > ingredientIndex ? amounts.get(ingredientIndex) : "";

            IngredientUse use = new IngredientUse();

            Optional<Ingredient> optionalIngredient = ingredientRepository.findByIngredientName(ingredientName);
            if (optionalIngredient.isPresent()) {
                use.setIngredient(optionalIngredient.get());
            } else {
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientName(ingredientName);
                ingredientRepository.save(ingredient);
                use.setIngredient(ingredient);
            }

            use.setAmount(amount);
            use.setRecipe(recipeToBeSaved);

            ingredientUseRepository.save(use);

            ingredientUses.add(use);
        }

        recipeToBeSaved.setIngredientUses(ingredientUses);

        recipeRepository.save(recipeToBeSaved);
        return "redirect:/recipe/detail/" + recipeToBeSaved.getName();
    }

    @GetMapping("/delete/{recipeId}")
    public String deleteRecipe(@PathVariable("recipeId") Long recipeId) {
        recipeRepository.deleteById(recipeId);

        return "redirect:/recipe/all";
    }

    @GetMapping("/detail/{name}")
    public String showRecipeDetailpage(@PathVariable("name") String name, Model datamodel) {
        Optional<Recipe> recipeToShow = recipeRepository.findByName(name);

        if (recipeToShow.isEmpty()) {
            return "redirect:/recipe/all";
        }

        datamodel.addAttribute("recipe", recipeToShow.get());

        return "recipeDetails";
    }

    public String showRecipeForm(Model datamodel, Recipe recipe) {
        datamodel.addAttribute("formRecipe", recipe);
        datamodel.addAttribute("allCategories", categoryRepository.findAll());

        return "recipeForm";
    }

}
