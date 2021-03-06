package softuni.meal_plan.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import softuni.meal_plan.model.service.PlannedMealServiceModel;
import softuni.meal_plan.service.IngredientService;
import softuni.meal_plan.service.PlannedMealService;
import softuni.meal_plan.service.RecipeService;
import softuni.meal_plan.web.annotations.PageTitle;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/planned")
public class MealPlanController extends BaseController {

    private final ModelMapper modelMapper;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final PlannedMealService plannedMealService;

    public MealPlanController(ModelMapper modelMapper, RecipeService recipeService, IngredientService ingredientService, PlannedMealService plannedMealService) {
        this.modelMapper = modelMapper;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.plannedMealService = plannedMealService;
    }

    @GetMapping("/meals")
    @PageTitle("All planned meals")
    public ModelAndView showAllPlannedMeals(ModelAndView modelAndView) {
        getAllPlannedMeals(modelAndView);
        return super.view("recipe/all-planned-meals", modelAndView);
    }

    @PostMapping(value = "/meals", params = "deleteFromPlan")
    @PageTitle("All planned meals")
    public ModelAndView deletePlannedMeal(@RequestParam("plannedMealId") String plannedMealId, ModelAndView modelAndView) {
        this.plannedMealService.deletePlannedMeal(plannedMealId);
        getAllPlannedMeals(modelAndView);
        return super.view("recipe/all-planned-meals", modelAndView);
    }

    private void getAllPlannedMeals(ModelAndView modelAndView) {
        List<PlannedMealServiceModel> plannedMealsList = this.plannedMealService.findAllPlannedMealsByUsername()
                .stream()
                .map(pr -> this.modelMapper.map(pr, PlannedMealServiceModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("plannedMeals", plannedMealsList);
    }


}
