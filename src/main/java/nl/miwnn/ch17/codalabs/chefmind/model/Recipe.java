package nl.miwnn.ch17.codalabs.chefmind.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

/**
 * @author Assib Pajman
 * An entity that includes a set of instructions and other details for preparing a dish
 */
@Entity
public class Recipe {

    @Id @GeneratedValue
    private Long recipeId;

    @Column(unique = true)
    @NotNull(message = "Recipe name is required")
    private String name;

    @ManyToOne
    private ChefMindUser author;

    @NotNull(message = "Serving size is required")
    @Min(value = 1, message = "Serving size must be between 1 and 1000")
    @Max(value = 1000, message = "Serving size must be between 1 and 1000")
    private Integer servingSize;

    @NotNull(message = "Prep time is required")
    @PositiveOrZero(message = "Prep time cannot be negative")
    private Integer prepTime;

    @NotNull(message = "Cooking time is required")
    @PositiveOrZero(message = "Cooking time cannot be negative")
    private Integer cookingTime;

    private String image = "https://arthurmillerfoundation.org/wp-content/uploads/2018/06/default-placeholder.png";

    @ElementCollection
    @Column(columnDefinition = "TEXT")
    private List<String> instructions;

    @OneToMany(mappedBy="recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientUse> ingredientUses;

    @ManyToMany
    private List<Category> categories;

    public Integer calculateTotalTime() {
        return this.prepTime + this.cookingTime;
    }

    public Long calculateTotalKcalPerServing() {
        if (servingSize <= 0) {
            throw new IllegalStateException("Serving size must be greater than 0");
        }

        Double totalKcal = 0.0;
        for (IngredientUse ingredientUse : ingredientUses) {
            totalKcal += ingredientUse.calculateKcalIngredientUse();
        }
        return Math.round(totalKcal / servingSize);
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

    public ChefMindUser getAuthor() {
        return author;
    }

    public void setAuthor(ChefMindUser author) {
        this.author = author;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
