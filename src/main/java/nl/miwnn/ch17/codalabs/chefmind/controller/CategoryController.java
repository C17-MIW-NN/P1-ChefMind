package nl.miwnn.ch17.codalabs.chefmind.controller;

import nl.miwnn.ch17.codalabs.chefmind.model.Category;
import nl.miwnn.ch17.codalabs.chefmind.repositories.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Harm van der Weide
 * Handles the categories on the recipe website
 */

@Controller
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/category/all")
    private String showCategoryOverview (Model datamodel){
        datamodel.addAttribute("allcategories", categoryRepository.findAll());
        datamodel.addAttribute("newCategory", new Category());

        return "categoryOverview";
    }
}
