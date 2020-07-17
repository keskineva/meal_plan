package softuni.meal_plan.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.meal_plan.model.entity.Ingredient;
import softuni.meal_plan.model.service.IngredientServiceModel;
import softuni.meal_plan.repository.IngredientRepository;
import softuni.meal_plan.service.IngredientService;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, ModelMapper modelMapper) {
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<IngredientServiceModel> saveIngredientList(List<String> ingredients) {
        List<Ingredient> ingredientList = new ArrayList<>();
        for (String oneIngredient : ingredients) {
            ingredientList.add(new Ingredient(oneIngredient));
        }
        // TODO filter existing entitites!!!
        List<Ingredient> savedIngredients = this.ingredientRepository.saveAll(ingredientList);

        List<IngredientServiceModel> ingredientServiceModelList = new ArrayList<>();
        for (Ingredient oneIngr : savedIngredients) {
            ingredientServiceModelList.add(new IngredientServiceModel(oneIngr.getName()));
        }
        return ingredientServiceModelList;
    }

    @Override
    public List<Ingredient> findAll() {
        return this.ingredientRepository.findAll();
    }

    @Override
    public IngredientServiceModel findIngredientById(String id) {
        return this.ingredientRepository.findById(id)
                .map(i -> this.modelMapper.map(i, IngredientServiceModel.class))
                .orElse(null);
    }
}
