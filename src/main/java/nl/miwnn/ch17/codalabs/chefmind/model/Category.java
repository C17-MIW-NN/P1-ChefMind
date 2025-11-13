package nl.miwnn.ch17.codalabs.chefmind.model;

import jakarta.persistence.*;

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

//    @Override
//    public String toString() {
//        return categoryName;
//    }
}
