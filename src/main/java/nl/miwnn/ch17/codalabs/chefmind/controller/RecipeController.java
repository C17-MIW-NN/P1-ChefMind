package nl.miwnn.ch17.codalabs.chefmind.controller;

import nl.miwnn.ch17.codalabs.chefmind.model.Ingredient;
import nl.miwnn.ch17.codalabs.chefmind.model.IngredientUse;
import nl.miwnn.ch17.codalabs.chefmind.model.Recipe;
import nl.miwnn.ch17.codalabs.chefmind.repositories.*;
import nl.miwnn.ch17.codalabs.chefmind.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Assib Pajman
 * Handles requests regarding recipes
 */
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientUseRepository ingredientUseRepository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    public RecipeController(RecipeRepository recipeRepository, IngredientRepository ingredientRepository,
                            IngredientUseRepository ingredientUseRepository, CategoryRepository categoryRepository, ImageRepository imageRepository, ImageService imageService, ImageRepository imageRepository1) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientUseRepository = ingredientUseRepository;
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
        this.imageRepository = imageRepository1;
    }

    @GetMapping("/all")
    public String showRecipeOverview(Model datamodel, Recipe recipe) {
        datamodel.addAttribute("recipes", recipeRepository.findAll());
        datamodel.addAttribute("formRecipe", new Recipe());

        return "recipeOverview";
    }

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
                                     @RequestParam(value = "ingredientNames[]", required = false)
                                     List<String> ingredientNames,
                                     @RequestParam(value = "amounts[]", required = false) List<String> amounts,
                                     BindingResult result,
                                     Model datamodel,
                                     @RequestParam MultipartFile recipeImage) {

        saveImage(recipeToBeSaved, result, recipeImage);

        checkForSameName(recipeToBeSaved, result);

        if (result.hasErrors()) {
            return showRecipeForm(datamodel, recipeToBeSaved);
        }

        if (ingredientNames == null) {
            ingredientNames = new ArrayList<>();
        }

        List<IngredientUse> ingredientUses = new ArrayList<>();
        makeIngredientUses(recipeToBeSaved, ingredientNames, amounts, ingredientUses);

        recipeToBeSaved.setIngredientUses(ingredientUses);

        recipeRepository.save(recipeToBeSaved);
        return "redirect:/recipe/detail/" + recipeToBeSaved.getName();
    }

    private void saveImage(Recipe recipeToBeSaved, BindingResult result, MultipartFile recipeImage) {
        try {
            if (!recipeImage.isEmpty()) {
                if (!imageRepository.existsByFileName(recipeImage.getOriginalFilename())) {
                    imageService.saveImage(recipeImage);
                }
                recipeToBeSaved.setImage("/image/" + recipeImage.getOriginalFilename());
            }
        } catch (IOException imageError) {
            result.rejectValue("recipeImage", "imageNotSaved", "Image not saved");
        }
    }

    private void makeIngredientUses(Recipe recipeToBeSaved, List<String> ingredientNames, List<String> amounts,
                                    List<IngredientUse> ingredientUses) {
        for (int ingredientIndex = 0; ingredientIndex < ingredientNames.size(); ingredientIndex++) {
            String ingredientName = ingredientNames.get(ingredientIndex);
            String amount = amounts.size() > ingredientIndex ? amounts.get(ingredientIndex) : "";

            IngredientUse use = new IngredientUse();

            useExistingIngredientOrMakeNewIngredient(ingredientName, use);

            use.setAmount(amount);
            use.setRecipe(recipeToBeSaved);

            ingredientUses.add(use);
        }
    }

    private void useExistingIngredientOrMakeNewIngredient(String ingredientName, IngredientUse use) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findByIngredientName(ingredientName);
        if (optionalIngredient.isPresent()) {
            use.setIngredient(optionalIngredient.get());
        } else {
            Ingredient ingredient = new Ingredient();
            ingredient.setIngredientName(ingredientName);
            ingredientRepository.save(ingredient);
            use.setIngredient(ingredient);
        }
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
