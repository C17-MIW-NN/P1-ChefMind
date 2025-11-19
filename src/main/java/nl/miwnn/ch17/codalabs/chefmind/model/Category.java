package nl.miwnn.ch17.codalabs.chefmind.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * @author Harm van der Weide
 * A label that catagorises the recipe.
 */
@Entity
public class Category {

    @Id @GeneratedValue
    Long categoryId;

    @Column(unique = true)
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private List<Recipe> recipes;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

//    @Override
//    public String toString() {
//        return categoryName;
//    }
}
