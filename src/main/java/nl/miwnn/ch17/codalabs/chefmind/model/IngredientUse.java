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

    public static final double QUANTITY_GRAMS_FOR_CALCULATING_KCAL = 100.0;
    @Id
    @GeneratedValue
    private Long ingredientUseId;

    private String amount;
    private Integer quantityInGrams;

    @ManyToOne
    private Ingredient ingredient;

    @ManyToOne
    private Recipe recipe;

    public Double calculateKcalIngredientUse() {
        if (quantityInGrams < 0 || ingredient.getKcalPer100g() < 0) {
            throw new IllegalArgumentException("cannot have negative quantities or Kcal per 100g");
        }

        return quantityInGrams * (ingredient.getKcalPer100g() / QUANTITY_GRAMS_FOR_CALCULATING_KCAL);
    }

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

    public Integer getQuantityInGrams() {
        return quantityInGrams;
    }

    public void setQuantityInGrams(Integer quantityInGrams) {
        this.quantityInGrams = quantityInGrams;
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
