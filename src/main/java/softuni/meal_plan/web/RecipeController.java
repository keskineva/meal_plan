package softuni.meal_plan.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.meal_plan.model.service.RecipeServiceModel;
import softuni.meal_plan.service.RecipeService;
import softuni.meal_plan.web.annotations.PageTitle;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/recipes")
public class RecipeController extends BaseController{
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

        modelAndView.addObject("recipes",recipes);
        return super.view("recipe/all-recipes", modelAndView);
    }
}
