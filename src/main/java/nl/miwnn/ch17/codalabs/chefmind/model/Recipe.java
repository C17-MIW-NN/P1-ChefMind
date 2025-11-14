package nl.miwnn.ch17.codalabs.chefmind.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

/**
 * @author Assib Pajman
 *
 */
@Entity
public class Recipe {

    @Id @GeneratedValue
    private Long recipeId;

    @Column(unique = true)
    private String name;
    private Integer servingSize;
    private Integer prepTime;
    private Integer cookingTime;
    private Integer time;
    private String image = "https://arthurmillerfoundation.org/wp-content/uploads/2018/06/default-placeholder.png";

    @ElementCollection
    @Column(columnDefinition = "TEXT")
    private List<String> instructions;

    @OneToMany(mappedBy="recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientUse> ingredientUses;

    @ManyToMany
    @OrderBy("categoryName ASC")
    private Set<Category> categories;

    public Integer calculateTotalTime() {
        return this.prepTime + this.cookingTime;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getServingSize() {
        return servingSize;
    }

    public void setServingSize(Integer servingSize) {
        this.servingSize = servingSize;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public List<IngredientUse> getIngredientUses() {
        return ingredientUses;
    }

    public void setIngredientUses(List<IngredientUse> ingredientUses) {
        this.ingredientUses = ingredientUses;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
