package nl.miwnn.ch17.codalabs.chefmind.controller;

import nl.miwnn.ch17.codalabs.chefmind.model.Category;
import nl.miwnn.ch17.codalabs.chefmind.repositories.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private String showCategoryOverview (Model datamodel){
        datamodel.addAttribute("allCategories", categoryRepository.findAll());

        return "categoryOverview";
    }

    @PostMapping("/add")
    private String showCategoryForm (Model datamodel){
        datamodel.addAttribute("newCategory", new Category());

        return "categoryForm";
    }

}
