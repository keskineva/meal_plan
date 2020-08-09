package softuni.meal_plan.service.impl;

import com.bethecoder.ascii_table.ASCIITable;
import org.springframework.stereotype.Service;
import softuni.meal_plan.model.service.PlannedMealServiceModel;
import softuni.meal_plan.model.service.RecipeIngredientServiceModel;
import softuni.meal_plan.service.OrderService;
import softuni.meal_plan.service.PlannedMealService;
import softuni.meal_plan.service.RecipeIngredientService;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final RecipeIngredientService recipeIngredientService;
    private final PlannedMealService plannedMealService;

    public OrderServiceImpl(RecipeIngredientService recipeIngredientService, PlannedMealService plannedMealService) {
        this.recipeIngredientService = recipeIngredientService;
        this.plannedMealService = plannedMealService;
    }

    public String getShoppingListOnly() {
        String[] header = {"Bought?", "Ingredient", "Amount in grams or count"};
        Map<String, Integer> totalIngredientsAndAmountsMap = getTotalIngredientsMap();
        String[][] data = mapToMatrix(totalIngredientsAndAmountsMap);
        return ASCIITable.getInstance().getTable(header, data);
    }

    public StringBuilder getCompleteMealPlan() {
        String table = getShoppingListOnly();
        // recipes list
        List<PlannedMealServiceModel> allPlannedMeals = plannedMealService.findAllPlannedMealsByUsername();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Shopping List\n");
        stringBuilder.append(table);
        stringBuilder.append("\n\n");
        stringBuilder.append("Complete Meal Plan\n");
        Date lastDatePrinted = null;
        for (PlannedMealServiceModel onePlannedMeal : allPlannedMeals) {
            Date currentDate = onePlannedMeal.getPlannedDateTime();
            if (lastDatePrinted == null || currentDate.compareTo(lastDatePrinted) != 0) {
                stringBuilder.append("|--------------------------------------------\n");
                stringBuilder.append("|--------- ").append(currentDate).append("\n");
                lastDatePrinted = currentDate;
            }
            // todo add breakfast / lunch
            stringBuilder.append("|--------------------------------------------\n");
            stringBuilder.append("|").append(onePlannedMeal.getRecipe().getName()).append("\n");
            stringBuilder.append("|Products for ").append(onePlannedMeal.getPlannedPortionsCount()).append(" portions:\n");

            String recipeId = onePlannedMeal.getRecipe().getId();
            //4. recipe_ingredients -> ingredients and amounts
            List<RecipeIngredientServiceModel> ingredientsAmounts =
                    recipeIngredientService.findIngredientsAndAmounts(recipeId);

            String[] recipeHeader = {"Ingredient", "Amount in grams or count"};
            String[][] recipeIngredientsData = new String[ingredientsAmounts.size()][];
            int index = 0;
            for (RecipeIngredientServiceModel ingredientAmount : ingredientsAmounts) {
                String ingredientName = ingredientAmount.getIngredient().getName();
                double totalAmountNeeded = ((double) ingredientAmount.getAmount() / (double) onePlannedMeal.getRecipe().getPortionsCount()) * (double) onePlannedMeal.getPlannedPortionsCount();
                recipeIngredientsData[index] = new String[]{ingredientName, "" + (int)Math.ceil(totalAmountNeeded)};
                index++;
            }
            String recipeIngredientsTable = ASCIITable.getInstance().getTable(recipeHeader, recipeIngredientsData);
            stringBuilder.append(recipeIngredientsTable);
            stringBuilder.append(onePlannedMeal.getRecipe().getInstructions()).append("\n\n");
        }
        return stringBuilder;
    }

    private String[][] mapToMatrix(Map<String, Integer> totalIngredientsAndAmountsMap) {
        String[][] data;
        data = new String[totalIngredientsAndAmountsMap.size()][];
        int index = 0;
        for (Map.Entry<String, Integer> oneIngredientPair : totalIngredientsAndAmountsMap.entrySet()) {
            data[index] = new String[]{"[ ]", oneIngredientPair.getKey(), oneIngredientPair.getValue().toString()};
            index++;
        }
        return data;
    }

    public Map<String, Integer> getTotalIngredientsMap() {
        //1. take all planned meals
        List<PlannedMealServiceModel> allPlannedMeals = plannedMealService.findAllPlannedMealsByUsername();
        Map<String, Integer> totalIngredientsAndAmountsMap = new LinkedHashMap<>();
        //2. foreach plannedMeal: plannedMeal.portions*
        for (PlannedMealServiceModel onePlannedMeal : allPlannedMeals) {
            double desiredPortions = onePlannedMeal.getPlannedPortionsCount();
            double portionsInOneRecipe = onePlannedMeal.getRecipe().getPortionsCount();
            //3. planned meal.recipe
            String recipeId = onePlannedMeal.getRecipe().getId();
            //4. recipe_ingredients -> ingredients and amounts
            List<RecipeIngredientServiceModel> ingredientsAmounts =
                    recipeIngredientService.findIngredientsAndAmounts(recipeId);

            for (RecipeIngredientServiceModel ingredientAmount : ingredientsAmounts) {
                String ingredientName = ingredientAmount.getIngredient().getName();
                double totalAmountNeeded = ((double) ingredientAmount.getAmount() / portionsInOneRecipe) * desiredPortions;
                //add to Map
                if (totalIngredientsAndAmountsMap.containsKey(ingredientName)) {
                    int oldAmount = totalIngredientsAndAmountsMap.get(ingredientName);
                    totalIngredientsAndAmountsMap.replace(ingredientName, (int) Math.ceil(oldAmount + totalAmountNeeded));
                } else {
                    totalIngredientsAndAmountsMap.put(ingredientName, (int) Math.ceil(totalAmountNeeded));
                }
            }

        }
        return totalIngredientsAndAmountsMap;
    }
}
