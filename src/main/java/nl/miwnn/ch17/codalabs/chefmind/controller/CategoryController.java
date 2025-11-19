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

        if (name == null) {
            datamodel.addAttribute("recipes", recipeRepository.findAll());
            datamodel.addAttribute("activeCategory", null);
        } else {
            Category category = categoryRepository.findByCategoryName(name).orElse(null);

            if (category == null) {
                return "redirect:/category/all";
            }

            List<Recipe> recipesPerCategory = recipeRepository.findByCategories_CategoryId(category.getCategoryId());

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
    public String saveNewCategory (@ModelAttribute("formCategory") Category categoryToBeSaved,
                                   BindingResult result,
                                   Model datamodel) {
        Optional<Category> categoryWithSameName =
                categoryRepository.findByCategoryName(categoryToBeSaved.getCategoryName());

        if (categoryWithSameName.isPresent() &&
                !categoryWithSameName.get().getCategoryId().equals(categoryToBeSaved.getCategoryId())) {
            result.addError(new FieldError("formCategory",
                    "categoryName",
                    "Category name already exists"));
            return "redirect:/category/all";
        }

        if (categoryToBeSaved.getCategoryName() == null){
            result.addError(new FieldError("formCategory",
                    "categoryName",
                    "Please do not leave field blank"));
            return "redirect:/category/all";
        }

        if (result.hasErrors()) {
            return showCategoryForm(datamodel, categoryToBeSaved);
        }

        categoryRepository.save(categoryToBeSaved);
        return "redirect:/category/all";
    }

    public String showCategoryForm(Model datamodel, Category category) {
        datamodel.addAttribute("formCategory", category);

        return "categoryForm";
    }

}
