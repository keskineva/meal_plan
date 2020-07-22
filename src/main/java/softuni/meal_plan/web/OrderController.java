package softuni.meal_plan.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.meal_plan.model.service.PlannedMealServiceModel;
import softuni.meal_plan.model.service.RecipeIngredientServiceModel;
import softuni.meal_plan.service.PlannedMealService;
import softuni.meal_plan.service.RecipeIngredientService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController extends BaseController {

    //todo

    private final RecipeIngredientService recipeIngredientService;
    private final ModelMapper modelMapper;
    private final PlannedMealService plannedMealService;


    public OrderController(RecipeIngredientService recipeIngredientService, ModelMapper modelMapper, PlannedMealService plannedMealService) {
        this.recipeIngredientService = recipeIngredientService;
        this.modelMapper = modelMapper;
        this.plannedMealService = plannedMealService;
    }

    @GetMapping(value = "/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createOrder(ModelAndView modelAndView) {
        return super.view("order/create-order", modelAndView);
    }

    @PostMapping(value = "/create", params = {"order"})
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createOrderConfirm(ModelAndView modelAndView) {
        //1. take all planned meals
        List<PlannedMealServiceModel> allPlannedMeals = plannedMealService.findAllPlannedMealsByUsername();
        Map<String, Integer> totalIngredientsAndAmountsMap = new LinkedHashMap<>();
        //2. foreach plannedmeal: plannedmeal.portions*
        for (PlannedMealServiceModel onePlannedMeal : allPlannedMeals) {
            int desiredPortions = onePlannedMeal.getPlannedPortionsCount();
            int portionsInOneRecipe = onePlannedMeal.getRecipe().getPortionsCount();
            //3. planned meal.recipe
            String recipeId = onePlannedMeal.getRecipe().getId();
            //4. recipe_ingredients -> ingredients and amounts
            List<RecipeIngredientServiceModel> ingredientsAmounts =
                    recipeIngredientService.ingredientsAndAmounts(recipeId);

            for (RecipeIngredientServiceModel ingredientAmount : ingredientsAmounts) {
                String ingredientName = ingredientAmount.getIngredient().getName();
                int totalAmountNeeded = (ingredientAmount.getAmount() / portionsInOneRecipe) * desiredPortions;
                //add to Map
                if (totalIngredientsAndAmountsMap.containsKey(ingredientName)) {
                    int oldAmount = totalIngredientsAndAmountsMap.get(ingredientName);
                    totalIngredientsAndAmountsMap.replace(ingredientName, oldAmount + totalAmountNeeded);
                } else {
                    totalIngredientsAndAmountsMap.put(ingredientName, totalAmountNeeded);
                }
            }

        }
        //print in html
        modelAndView.addObject("totalIngredientsAndAmountsMap", totalIngredientsAndAmountsMap);
        return super.view("order/create-order", modelAndView);
    }
}