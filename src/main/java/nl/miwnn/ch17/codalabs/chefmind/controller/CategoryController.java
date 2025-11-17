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
    public String showCategoryOverview (Model datamodel, Category category){
        datamodel.addAttribute("allCategories", categoryRepository.findAll());
        datamodel.addAttribute("formCategory", new Category());

        return "categoryOverview";
    }

    @GetMapping("/add")
    public String showCategoryForm(Model datamodel) {
        return showCategoryForm(datamodel, new Category());
    }


    @GetMapping("/detail/{name}")
    public String showCategoryDetailpage(@PathVariable("name") String name,
                                         Model datamodel,
                                         Long categoryId) {
        Optional<Category> categoryToShow = categoryRepository.findByCategoryName(name);

        if (categoryToShow.isEmpty()) {
            return "redirect:/category/all";
        }

        Category category = categoryToShow.get();

        List<Recipe> recipesPerCategory = recipeRepository.findByCategories_CategoryId(category.getCategoryId());

        datamodel.addAttribute("category", category);
        datamodel.addAttribute("recipes", recipesPerCategory);

        return "categoryDetails";
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
        }

        if (result.hasErrors()) {
            return showCategoryForm(datamodel, categoryToBeSaved);
        }

        categoryRepository.save(categoryToBeSaved);
        return "redirect:/category/all";
    }

    @PostMapping("/save")
    public String saveOrUpdateCategory (@ModelAttribute("formCategory") Category categoryToBeSaved,
                                     BindingResult result,
                                     Model datamodel) {
        Optional<Category> categoryWithSameName =
                categoryRepository.findByCategoryName(categoryToBeSaved.getCategoryName());

        if (categoryWithSameName.isPresent() &&
                !categoryWithSameName.get().getCategoryId().equals(categoryToBeSaved.getCategoryId())) {
            result.addError(new FieldError("formCategory",
                    "categoryName",
                    "Category name already exists"));
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
