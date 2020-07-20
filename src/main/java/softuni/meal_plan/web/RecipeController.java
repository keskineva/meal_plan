package softuni.meal_plan.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.meal_plan.error.Constants;
import softuni.meal_plan.model.binding.RecipeAddBindingModel;
import softuni.meal_plan.model.entity.User;
import softuni.meal_plan.model.service.IngredientServiceModel;
import softuni.meal_plan.model.service.PlannedMealServiceModel;
import softuni.meal_plan.model.service.RecipeServiceModel;
import softuni.meal_plan.model.service.UserServiceModel;
import softuni.meal_plan.service.IngredientService;
import softuni.meal_plan.service.PlannedMealService;
import softuni.meal_plan.service.RecipeService;
import softuni.meal_plan.web.annotations.PageTitle;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/recipes")
public class RecipeController extends BaseController {

    public static enum MealType {

        BREAKFAST("BREAKFAST"),
        LUNCH("LUNCH"),
        DINNER("DINNER"),
        SNACK("SNACK");

        private String type;

        MealType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return this.getType();
        }

    }


    private final ModelMapper modelMapper;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final PlannedMealService plannedMealService;


    @Autowired
    public RecipeController(ModelMapper modelMapper, RecipeService recipeService, IngredientService ingredientService, PlannedMealService plannedMealService) {
        this.modelMapper = modelMapper;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.plannedMealService = plannedMealService;
    }

    @GetMapping("/all")
    @PageTitle("All recipes")
    public ModelAndView showAllRecipes(ModelAndView modelAndView) {
        List<RecipeServiceModel> recipes = this.recipeService.findAllRecipes()
                .stream()
                .map(r -> this.modelMapper.map(r, RecipeServiceModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("recipes", recipes);
        return super.view("recipe/all-recipes", modelAndView);
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add Recipe")
    public String add(@Valid @ModelAttribute("recipeAddBindingModel") RecipeAddBindingModel recipeAddBindingModel) {
        return "recipe/add-recipe";
    }

    @PostMapping(value = "/add", params = {"save"})
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addConfirm(@Valid @ModelAttribute("recipeAddBindingModel") RecipeAddBindingModel recipeAddBindingModel,
                                   BindingResult bindingResult,
                                   ModelAndView modelAndView,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            for (ObjectError oneError : bindingResult.getAllErrors()) {
                System.err.println(oneError.toString());
            }
            redirectAttributes.addFlashAttribute("recipeAddBindingModel", recipeAddBindingModel);
            modelAndView.setViewName("redirect:/recipes/add");
            return modelAndView;
        } else {

            List<RecipeAddBindingModel.Row> ingredientsList = recipeAddBindingModel.getIngredientsList();
            List<String> ingredients = new LinkedList<>();
            for (RecipeAddBindingModel.Row ingredient : ingredientsList) {
                ingredients.add(ingredient.getIngredient());
            }
            List<IngredientServiceModel> ingredientEntityList = this.ingredientService.saveIngredientList(ingredients);
            RecipeServiceModel recipeServiceModel = this.modelMapper.map(recipeAddBindingModel, RecipeServiceModel.class);
            recipeServiceModel.setIngredients(ingredientEntityList);
            this.recipeService.addRecipe(recipeServiceModel);
            return super.redirect("/recipes/all");
        }
    }

    @PostMapping(value = "/add", params = {"addRow"})
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addRow(final RecipeAddBindingModel recipeAddBindingModel,
                               final BindingResult bindingResult,
                               ModelAndView modelAndView,
                               RedirectAttributes redirectAttributes) {
        recipeAddBindingModel.getIngredientsList().add(new RecipeAddBindingModel.Row());
        modelAndView.addObject("recipes", recipeAddBindingModel);
        return super.view("recipe/add-recipe", modelAndView);
    }

    @PostMapping(value = "/add", params = {"removeRow"})
    @PreAuthorize("isAuthenticated()")
    public ModelAndView removeRow(final RecipeAddBindingModel recipeAddBindingModel,
                                  final BindingResult bindingResult,
                                  final HttpServletRequest req,
                                  ModelAndView modelAndView) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        recipeAddBindingModel.getIngredientsList().remove(rowId.intValue());
        modelAndView.addObject("recipes", recipeAddBindingModel);
        return super.view("recipe/add-recipe", modelAndView);
    }

    @PostMapping(value = "/all", params = {"addToPlan"})
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addToPlan(@RequestParam("portions_count") Integer portionsCount,
                                  @RequestParam("recipe_id") String recipeId,
                                  @RequestParam(value = "planned_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                  @RequestParam("meal_type") MealType mealType,
                                  ModelAndView modelAndView) {

        Date mealDateTime = createMealDateTime(date, mealType);

        PlannedMealServiceModel plannedMealServiceModel = new PlannedMealServiceModel();
        plannedMealServiceModel.setPlannedPortionsCount(portionsCount);
        plannedMealServiceModel.setPlannedDateTime(mealDateTime);
        plannedMealServiceModel.setRecipe(recipeService.findRecipeById(recipeId));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        plannedMealServiceModel.setUser(this.modelMapper.map(user, UserServiceModel.class));
        this.plannedMealService.addMealToPlan(plannedMealServiceModel);

        return super.redirect("/recipes/all");
    }

    private Date createMealDateTime(Date date, MealType mealType) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        if (MealType.BREAKFAST.equals(mealType)) {
            cal.set(Calendar.HOUR_OF_DAY, Constants.BREAKFAST_HOUR);

        } else if (MealType.LUNCH.equals(mealType)) {
            cal.set(Calendar.HOUR_OF_DAY, Constants.LUNCH_HOUR);

        } else if (MealType.DINNER.equals(mealType)) {
            cal.set(Calendar.HOUR_OF_DAY, Constants.DINNER_HOUR);

        } else if (MealType.SNACK.equals(mealType)) {
            cal.set(Calendar.HOUR_OF_DAY, Constants.SNACK_HOUR);

        } else {
            System.err.println("Invalid Meal Type:" + mealType);
        }
        return cal.getTime();
    }
}
