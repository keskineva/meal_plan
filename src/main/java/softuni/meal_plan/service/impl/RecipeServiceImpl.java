package softuni.meal_plan.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.meal_plan.model.service.RecipeServiceModel;
import softuni.meal_plan.repository.RecipeRepository;
import softuni.meal_plan.service.RecipeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RecipeServiceModel addRecipe(RecipeServiceModel recipeServiceModel) {
        return null;
    }

    @Override
    public List<RecipeServiceModel> findAllRecipes() {
        return this.recipeRepository.findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RecipeServiceModel.class))
                .collect(Collectors.toList());
    }
}
