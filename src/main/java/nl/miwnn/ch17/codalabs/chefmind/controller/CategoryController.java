package nl.miwnn.ch17.codalabs.chefmind.controller;

import nl.miwnn.ch17.codalabs.chefmind.model.Category;
import nl.miwnn.ch17.codalabs.chefmind.model.Recipe;
import nl.miwnn.ch17.codalabs.chefmind.repositories.CategoryRepository;
import nl.miwnn.ch17.codalabs.chefmind.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Harm van der Weide
 * Handles the categories on the recipe website
 */

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    public CategoryController(CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/all")
    public String showCategoryOverview (@RequestParam(value = "name", required = false) String name,
                                            Model datamodel){
        datamodel.addAttribute("allCategories", categoryRepository.findAll());
        datamodel.addAttribute("formCategory", new Category());

        Optional<Category> category = categoryRepository.findByCategoryName(name);

        if (category.isEmpty()) {
            datamodel.addAttribute("recipes", recipeRepository.findAll());
        } else {
            List<Recipe> recipesPerCategory = recipeRepository.findByCategories_CategoryId(category.get().getCategoryId());

            datamodel.addAttribute("recipes", recipesPerCategory);
            datamodel.addAttribute("activeCategory", name);
        }

        return "categoryOverview";
    }

    @GetMapping("/add")
    public String showCategoryForm(Model datamodel) {
        return showCategoryForm(datamodel, new Category());
    }

    @PostMapping("/new")
    public String saveNewCategory (@ModelAttribute("formCategory") Category categoryToBeSaved, BindingResult result,
                                   Model datamodel) {
        Optional<Category> categoryWithSameName =
                categoryRepository.findByCategoryName(categoryToBeSaved.getCategoryName());

        String returnStringSameName = checkForSameName(categoryToBeSaved, result, categoryWithSameName);
        if (returnStringSameName != null) return returnStringSameName;

        String returnStringCategoryEmpty = checkIfCategoryIsEmpty(categoryToBeSaved, result);
        if (returnStringCategoryEmpty != null) return returnStringCategoryEmpty;

        if (result.hasErrors()) {
            return showCategoryForm(datamodel, categoryToBeSaved);
        }

        categoryRepository.save(categoryToBeSaved);
        return "redirect:/category/all";
    }

    private static String checkIfCategoryIsEmpty(Category categoryToBeSaved, BindingResult result) {
        if (categoryToBeSaved.getCategoryName() == null){
            result.addError(new FieldError("formCategory", "categoryName",
                    "Please do not leave field blank"));
            return "redirect:/category/all";
        }
        return null;
    }

    private static String checkForSameName(Category categoryToBeSaved, BindingResult result, Optional<Category> categoryWithSameName) {
        if (categoryWithSameName.isPresent() &&
                !categoryWithSameName.get().getCategoryId().equals(categoryToBeSaved.getCategoryId())) {
            result.addError(new FieldError("formCategory", "categoryName",
                    "Category name already exists"));
            return "redirect:/category/all";
        }
        return null;
    }

    public String showCategoryForm(Model datamodel, Category category) {
        datamodel.addAttribute("formCategory", category);

        return "categoryForm";
    }
}
