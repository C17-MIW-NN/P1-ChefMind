package nl.miwnn.ch17.codalabs.chefmind.repositories;

import nl.miwnn.ch17.codalabs.chefmind.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Harm van der Weide
 * Purpose for the class
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
