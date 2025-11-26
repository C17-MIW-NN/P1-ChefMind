package nl.miwnn.ch17.codalabs.chefmind.controller;

import jakarta.validation.Valid;
import nl.miwnn.ch17.codalabs.chefmind.model.ChefMindUser;
import nl.miwnn.ch17.codalabs.chefmind.model.Ingredient;
import nl.miwnn.ch17.codalabs.chefmind.model.IngredientUse;
import nl.miwnn.ch17.codalabs.chefmind.model.Recipe;
import nl.miwnn.ch17.codalabs.chefmind.repositories.*;
import nl.miwnn.ch17.codalabs.chefmind.service.ImageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @author Assib Pajman
 * Handles requests regarding recipes
 */
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    public RecipeController(RecipeRepository recipeRepository, IngredientRepository ingredientRepository,
                            CategoryRepository categoryRepository, ImageRepository imageRepository,
                            ImageService imageService) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/new")
    public String showNewRecipeForm(Model datamodel, @AuthenticationPrincipal ChefMindUser loggedInUser) {
        Recipe newRecipe = new Recipe();
        newRecipe.setAuthor(loggedInUser);
        return showRecipeForm(datamodel, newRecipe);
    }

    @GetMapping("/edit/{name}")
    public String showEditRecipeForm(@PathVariable("name") String name, Model datamodel) {
        Optional<Recipe> optionalRecipe = recipeRepository.findByName(name);

        if (optionalRecipe.isPresent()) {
            return showRecipeForm(datamodel, optionalRecipe.get());
        }

        return "redirect:/category/all";
    }

    public String showRecipeForm(Model datamodel, Recipe recipe) {
        datamodel.addAttribute("formRecipe", recipe);
        datamodel.addAttribute("allCategories", categoryRepository.findAll());

        return "recipeForm";
    }

    @PostMapping("/save")
    public String saveOrUpdateRecipe(@ModelAttribute("formRecipe") @Valid Recipe recipeToBeSaved,
                                     BindingResult result, Model datamodel,
                                     @AuthenticationPrincipal ChefMindUser loggedInUser,
                      @RequestParam(value = "ingredientNames[]", required = false) List<String> ingredientNames,
                      @RequestParam(value = "quantities[]", required = false) List<Double> quantitiesInUnit,
                      @RequestParam(value = "units[]", required = false) List<String> units,
                      @RequestParam(value = "quantitiesInGrams[]", required = false) List<Integer> quantitiesInGrams,
                      @RequestParam(value = "kcalPer100g[]", required = false) List<Integer> kcalPer100gList,
                      @RequestParam MultipartFile recipeImage) {

        saveImage(recipeToBeSaved, result, recipeImage);

        checkForSameName(recipeToBeSaved, result);

        if (result.hasErrors()) {
            return showRecipeForm(datamodel, recipeToBeSaved);
        }

        if (ingredientNames == null) {
            ingredientNames = new ArrayList<>();
        }

        List<IngredientUse> ingredientUses = makeIngredientUses(recipeToBeSaved, ingredientNames,
                quantitiesInUnit, units, quantitiesInGrams, kcalPer100gList);
        recipeToBeSaved.setIngredientUses(ingredientUses);

        recipeToBeSaved.setAuthor(loggedInUser);

        recipeRepository.save(recipeToBeSaved);
        return "redirect:/recipe/detail/" + recipeToBeSaved.getName();
    }

    private void saveImage(Recipe recipeToBeSaved, BindingResult result, MultipartFile recipeImage) {
        try {
            if (!recipeImage.isEmpty()) {
                if (!imageRepository.existsByFileName(recipeImage.getOriginalFilename())) {
                    imageService.saveImage(recipeImage);
                }
                recipeToBeSaved.setImageURL("/image/" + recipeImage.getOriginalFilename());
            }
        } catch (IOException imageError) {
            result.rejectValue("recipeImage", "imageNotSaved", "Image not saved");
        }
    }

    private List<IngredientUse> makeIngredientUses(Recipe recipeToBeSaved, List<String> ingredientNames,
                                                   List<Double> quantitiesInUnit, List<String> units,
                                                   List<Integer> quantitiesInGrams, List<Integer> kcalPer100gList) {
        List<IngredientUse> ingredientUses = new ArrayList<>();
        for (int ingredientIndex = 0; ingredientIndex < ingredientNames.size(); ingredientIndex++) {
            String ingredientName = ingredientNames.get(ingredientIndex);
            Double quantityInUnit = quantitiesInUnit.size() > ingredientIndex &&
                    quantitiesInUnit.get(ingredientIndex) != null ? quantitiesInUnit.get(ingredientIndex) : 0;
            String unit = units.size() > ingredientIndex ? units.get(ingredientIndex) : "";
            Integer quantityInGrams = quantitiesInGrams.size() > ingredientIndex &&
                    quantitiesInGrams.get(ingredientIndex) != null ? quantitiesInGrams.get(ingredientIndex) : 0;
            Integer kcalPer100g = kcalPer100gList.size() > ingredientIndex &&
                    kcalPer100gList.get(ingredientIndex) != null ? kcalPer100gList.get(ingredientIndex) : 0;

            IngredientUse use = new IngredientUse();

            useExistingIngredientOrMakeNewIngredient(ingredientName, kcalPer100g, use);
            use.setQuantityInUnit(quantityInUnit);
            use.setUnit(unit);
            use.setRecipe(recipeToBeSaved);
            use.setQuantityInGrams(quantityInGrams);

            ingredientUses.add(use);
        }
        return ingredientUses;
    }

    private void useExistingIngredientOrMakeNewIngredient(String ingredientName, Integer kcalPer100g,
                                                          IngredientUse use) {
        Ingredient ingredientToBeUsed = new Ingredient();

        Optional<Ingredient> optionalIngredient = ingredientRepository.findByIngredientName(ingredientName);

        if (optionalIngredient.isPresent()) {
            ingredientToBeUsed = optionalIngredient.get();
        }

        ingredientToBeUsed.setIngredientName(ingredientName);
        ingredientToBeUsed.setKcalPer100g(kcalPer100g);
        ingredientRepository.save(ingredientToBeUsed);
        use.setIngredient(ingredientToBeUsed);
    }

    private void checkForSameName(Recipe recipeToBeSaved, BindingResult result) {
        Optional<Recipe> recipeWithSameName = recipeRepository.findByName(recipeToBeSaved.getName());

        if (recipeWithSameName.isPresent() &&
                !recipeWithSameName.get().getRecipeId().equals(recipeToBeSaved.getRecipeId())) {
            result.addError(new FieldError("recipe",
                    "name",
                    "Recipe name already exists"));
        }
    }

    @GetMapping("/delete/{recipeId}")
    public String deleteRecipe(@PathVariable("recipeId") Long recipeId) {
        recipeRepository.deleteById(recipeId);

        return "redirect:/category/all";
    }

    @GetMapping("/detail/{name}")
    public String showRecipeDetailpage(@PathVariable("name") String name, Model datamodel) {
        Optional<Recipe> recipeToShow = recipeRepository.findByName(name);

        if (recipeToShow.isEmpty()) {
            return "redirect:/category/all";
        }

        datamodel.addAttribute("recipe", recipeToShow.get());

        return "recipeDetails";
    }

    @GetMapping("/search")
    public String searchRecipes(@RequestParam("query") String query, Model datamodel) {
        Set<Recipe> results = searchRecipes(query);
        datamodel.addAttribute("recipes", results);
        datamodel.addAttribute("query", query);
        return "recipeSearch";
    }

    public Set<Recipe> searchRecipes(String query) {
        List<Recipe> titleResults = recipeRepository.findByNameContainingIgnoreCase(query);
        List<Recipe> ingredientResults =
                recipeRepository.findDistinctByIngredientUses_Ingredient_IngredientNameIgnoreCase(query);
        List<Recipe> categoryResults = recipeRepository.findDistinctByCategories_CategoryNameIgnoreCase(query);

        Set<Recipe> results = new HashSet<>();
        results.addAll(titleResults);
        results.addAll(ingredientResults);
        results.addAll(categoryResults);

        return results;
    }
}
