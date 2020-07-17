package softuni.meal_plan.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.meal_plan.model.binding.RecipeAddBindingModel;
import softuni.meal_plan.model.service.IngredientServiceModel;
import softuni.meal_plan.model.service.RecipeServiceModel;
import softuni.meal_plan.service.IngredientService;
import softuni.meal_plan.service.RecipeService;
import softuni.meal_plan.web.annotations.PageTitle;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/recipes")
public class RecipeController extends BaseController {

    private final ModelMapper modelMapper;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @Autowired
    public RecipeController(ModelMapper modelMapper, RecipeService recipeService, IngredientService ingredientService) {
        this.modelMapper = modelMapper;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @ModelAttribute("allRecipes")
    public List<RecipeServiceModel> populateRecipes() {
        List<RecipeServiceModel> recipes = this.recipeService.findAllRecipes()
                .stream()
                .map(r -> this.modelMapper.map(r, RecipeServiceModel.class))
                .collect(Collectors.toList());
        return recipes;
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


}
