package nl.miwnn.ch17.codalabs.chefmind.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * @author Nelleke Jansen
 * The concept of using an ingredient in a recipe.
 */
@Entity
public class IngredientUse {

    @Id
    @GeneratedValue
    private Long ingredientUseId;

    private String amount;

    @ManyToOne
    private Ingredient ingredient;

    @ManyToOne
    private Recipe recipe;

    public Long getIngredientUseId() {
        return ingredientUseId;
    }

    public void setIngredientUseId(Long ingredientUseId) {
        this.ingredientUseId = ingredientUseId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
