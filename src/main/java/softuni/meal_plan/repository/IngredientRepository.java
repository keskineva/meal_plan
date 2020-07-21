package softuni.meal_plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.meal_plan.model.entity.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, String> {
    Ingredient findByName(String name);
}
