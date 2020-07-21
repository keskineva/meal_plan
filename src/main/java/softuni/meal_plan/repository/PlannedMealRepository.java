package softuni.meal_plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.meal_plan.model.entity.PlannedMeal;

@Repository
public interface PlannedMealRepository extends JpaRepository<PlannedMeal, String> {
}