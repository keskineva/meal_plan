package softuni.meal_plan.service;

import java.util.Map;

public interface OrderService {
    StringBuilder getCompleteMealPlan();

    String getShoppingListOnly();

    Map<String, Integer> getTotalIngredientsMap();
}
