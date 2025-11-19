package nl.miwnn.ch17.codalabs.chefmind.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * @author Nelleke Jansen
 * A type of food product that can be used in a recipe.
 */
@Entity
public class Ingredient {

    @Id
    @GeneratedValue
    private Long ingredientId;

    @Column(unique = true)
    private String ingredientName;

    private Integer kcalPer100g;

    @OneToMany(mappedBy = "ingredient")
    private List<IngredientUse> ingredientUses;

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Integer getKcalPer100g() {
        return kcalPer100g;
    }

    public void setKcalPer100g(Integer kcalPer100g) {
        this.kcalPer100g = kcalPer100g;
    }

    public List<IngredientUse> getIngredientUses() {
        return ingredientUses;
    }

    public void setIngredientUses(List<IngredientUse> ingredientUses) {
        this.ingredientUses = ingredientUses;
    }
}
