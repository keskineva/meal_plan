package softuni.meal_plan.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.meal_plan.model.entity.RecipeIngredient;
import softuni.meal_plan.model.service.RecipeIngredientServiceModel;
import softuni.meal_plan.repository.RecipeIngredientRepository;
import softuni.meal_plan.service.RecipeIngredientService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepositoryRepository;
    private final ModelMapper modelMapper;
    @PersistenceContext
    public EntityManager em;

    public RecipeIngredientServiceImpl(RecipeIngredientRepository recipeRepository, ModelMapper modelMapper) {
        this.recipeIngredientRepositoryRepository = recipeRepository;

        this.modelMapper = modelMapper;
    }


    @Override
    public List<RecipeIngredientServiceModel> ingredientsAndAmounts(String id) {
        List<RecipeIngredient> entityList = em.createQuery("SELECT s from RecipeIngredient s where s.recipe.id LIKE :recipeId", RecipeIngredient.class)
                .setParameter("recipeId", id)
                .getResultList();
        List<RecipeIngredientServiceModel> result = new ArrayList<>();

        for (RecipeIngredient entity : entityList) {

            result.add(modelMapper.map(entity, RecipeIngredientServiceModel.class));
        }


        return result;
    }
}
