package softuni.meal_plan.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.meal_plan.error.Constants;
import softuni.meal_plan.model.binding.RecipeAddBindingModel;
import softuni.meal_plan.model.binding.RecipeShowBindingModel;
import softuni.meal_plan.model.service.*;
import softuni.meal_plan.service.*;
import softuni.meal_plan.web.annotations.PageFavicon;
import softuni.meal_plan.web.annotations.PageTitle;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/recipes")
@Validated
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
    private final RecipeIngredientService recipeIngredientService;
    private final UserService userService;

    @Autowired
    public RecipeController(ModelMapper modelMapper, RecipeService recipeService, IngredientService ingredientService, PlannedMealService plannedMealService, RecipeIngredientService recipeIngredientService, UserService userService) {
        this.modelMapper = modelMapper;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.plannedMealService = plannedMealService;
        this.recipeIngredientService = recipeIngredientService;
        this.userService = userService;
    }

    @PageFavicon("https://www.freepngimg.com/download/grocery/41636-2-groceries-png-image-high-quality.png")
    @GetMapping(value = "/show", params = {"id"})
    @PreAuthorize("isAuthenticated()")
    public ModelAndView showRecipe(@RequestParam("id") String recipeId, ModelAndView modelAndView) {
        RecipeServiceModel oneRecipe = recipeService.findRecipeById(recipeId);
        RecipeShowBindingModel recipeShowBindingModel = modelMapper.map(oneRecipe, RecipeShowBindingModel.class);
        recipeShowBindingModel.setIngredientsList(new ArrayList<>());

        List<RecipeIngredientServiceModel> recipeIngredientServiceModelList = recipeIngredientService.findIngredientsAndAmounts(oneRecipe.getId());
        for (RecipeIngredientServiceModel oneIngredientServiceModel : recipeIngredientServiceModelList) {
            recipeShowBindingModel.getIngredientsList().add(new RecipeShowBindingModel.Row(
                    oneIngredientServiceModel.getIngredient().getName(),
                    oneIngredientServiceModel.getAmount()));
        }

        modelAndView.addObject("recipeShowBindingModel", recipeShowBindingModel);
        modelAndView.setViewName("recipe/show-recipe");
        return modelAndView;
    }

    //adding recipe
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
            /*for (ObjectError oneError : bindingResult.getAllErrors()) {
                System.err.println(oneError.toString());
            }*/
            redirectAttributes.addFlashAttribute("recipeAddBindingModel", recipeAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeAddBindingModel",
                    bindingResult);
            /*redirectAttributes.addFlashAttribute("recipeAddBindingModel", recipeAddBindingModel);*/
            modelAndView.setViewName("redirect:/recipes/add");
            return modelAndView;
        } else {
            // 1.save in recipe table
            RecipeServiceModel recipeServiceModel = this.modelMapper.map(recipeAddBindingModel, RecipeServiceModel.class);
            recipeServiceModel = this.recipeService.addRecipe(recipeServiceModel);

            // 2. save in ingredients table
            List<RecipeAddBindingModel.Row> ingredientsList = recipeAddBindingModel.getIngredientsList();
            Map<IngredientServiceModel, Integer> ingredients = new LinkedHashMap<>();
            for (RecipeAddBindingModel.Row ingredient : ingredientsList) {
                if (ingredient.getIngredient().isBlank()) {
                    continue; // if ingredient is empty, just skip it!
                }

                IngredientServiceModel ingredientServiceModel = this.ingredientService.saveIngredient(ingredient.getIngredient());

                // recipe_ingredients table
                RecipeIngredientServiceModel model = new RecipeIngredientServiceModel();
                model.setAmount(ingredient.getAmount());
                model.setIngredient(ingredientServiceModel);
                model.setRecipe(recipeServiceModel);
                this.ingredientService.saveRecipeIngredient(model);
            }

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

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Delete Recipe")
    public ModelAndView deleteRecipe(@PathVariable String id, ModelAndView modelAndView) {
        RecipeServiceModel recipeServiceModel = this.recipeService.findRecipeById(id);

        modelAndView.addObject("recipe", recipeServiceModel);
        modelAndView.addObject("recipeId", id);

        return super.view("recipe/delete-recipe", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteRecipeConfirm(@PathVariable String id) {
        this.recipeService.deleteRecipe(id);

        return super.redirect("/recipes/all");
    }

    //view all recipes
    @GetMapping("/all")
    @PageTitle("All recipes")
    public ModelAndView showAllRecipes(ModelAndView modelAndView) {
        List<RecipeServiceModel> recipes = this.recipeService.findAllRecipes()
                .stream()
                .map(r -> this.modelMapper.map(r, RecipeServiceModel.class))
                .collect(Collectors.toList());

        LocalDate date = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = date.format(formatter);

        modelAndView.addObject("minDate", dateStr);//2020-07-27
        modelAndView.addObject("recipes", recipes);
        return super.view("recipe/all-recipes", modelAndView);
    }

    //adding recipe to plan
    @PostMapping(value = "/all", params = {"addToPlan"})
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public String addToPlan(@RequestParam("portions_count") @Min(1) Integer portionsCount,
                                  @RequestParam("recipe_id") @NotNull String recipeId,
                                  @RequestParam("planned_date") @DateTimeFormat(pattern = "yyyy-MM-dd") @FutureOrPresent Date date,
                                  @RequestParam("meal_type") @NotNull MealType mealType,
                                  ModelAndView modelAndView) {

        Date mealDateTime = createMealDateTime(date, mealType);

        PlannedMealServiceModel plannedMealServiceModel = new PlannedMealServiceModel();
        plannedMealServiceModel.setPlannedPortionsCount(portionsCount);
        plannedMealServiceModel.setPlannedDateTime(mealDateTime);
        plannedMealServiceModel.setRecipe(recipeService.findRecipeById(recipeId));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserServiceModel userServiceModel = userService.findUserByUserName(username);
        plannedMealServiceModel.setUser(userServiceModel);
        this.plannedMealService.addMealToPlan(plannedMealServiceModel);
        return "OK. Added.";
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
