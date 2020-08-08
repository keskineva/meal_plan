package softuni.meal_plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.meal_plan.model.entity.PlannedMeal;

import java.util.List;

@Repository
public interface PlannedMealRepository extends JpaRepository<PlannedMeal, String> {
  List<PlannedMeal> findPlannedMealsByUser_Username_OrderByPlannedDateTimeAsc(String username);
  void deletePlannedMealsByRecipe_Id(String recipeId);
}
