package nl.miwnn.ch17.codalabs.chefmind.controller;

import nl.miwnn.ch17.codalabs.chefmind.model.Category;
import nl.miwnn.ch17.codalabs.chefmind.model.Recipe;
import nl.miwnn.ch17.codalabs.chefmind.repositories.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Harm van der Weide
 * Handles the categories on the recipe website
 */

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/all")
    private String showCategoryOverview (Model datamodel, Category category){
        datamodel.addAttribute("allCategories", categoryRepository.findAll());
        datamodel.addAttribute("newCategory", new Category());

        return "categoryOverview";
    }

    @GetMapping("/add")
    public String showCategoryForm(Model datamodel) {
        return showCategoryForm(datamodel, new Category());
    }

    @PostMapping("/save")
    public String saveOrUpdateCategory (@ModelAttribute("formCategory") Category categoryToBeSaved,
                                     BindingResult result,
                                     Model datamodel) {
        Optional<Category> categoryWithSameName =
                categoryRepository.findByCategoryName(categoryToBeSaved.getCategoryName());

        if (categoryWithSameName.isPresent() &&
                !categoryWithSameName.get().getCategoryId().equals(categoryToBeSaved.getCategoryId())) {
            result.addError(new FieldError("Category",
                    "name",
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
