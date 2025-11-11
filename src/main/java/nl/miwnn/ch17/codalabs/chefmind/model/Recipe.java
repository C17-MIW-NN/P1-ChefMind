package nl.miwnn.ch17.codalabs.chefmind.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

/**
 * @author Assib Pajman
 *
 */
@Entity
public class Recipe {

    @Id @GeneratedValue
    private Long recipeId;

    private String name;
    private Integer servingSize;
    private Integer time;
    private String instructions;

    @OneToMany(mappedBy="recipe")
    private List<IngredientUse> ingredientUses;

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

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<IngredientUse> getIngredientUses() {
        return ingredientUses;
    }

    public void setIngredientUses(List<IngredientUse> ingredientUses) {
        this.ingredientUses = ingredientUses;
    }
}
