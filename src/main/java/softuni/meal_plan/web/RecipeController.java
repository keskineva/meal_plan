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
import softuni.meal_plan.model.service.RecipeServiceModel;
import softuni.meal_plan.service.RecipeService;
import softuni.meal_plan.web.annotations.PageTitle;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/recipes")
public class RecipeController extends BaseController {

    private final ModelMapper modelMapper;
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(ModelMapper modelMapper, RecipeService recipeService) {
        this.modelMapper = modelMapper;
        this.recipeService = recipeService;
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

    @PostMapping("/add")
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
            this.recipeService
                    .addRecipe(this.modelMapper.map(recipeAddBindingModel, RecipeServiceModel.class));

            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }


    }
}
