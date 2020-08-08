package softuni.meal_plan.scheduled;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import softuni.meal_plan.model.service.RecipeServiceModel;
import softuni.meal_plan.service.RecipeService;

import java.util.ArrayList;
import java.util.List;

class ScheduleReportTest {


    @Test
    void makeReport() {
        RecipeService recipeServiceMock = Mockito.mock(RecipeService.class);
        List<RecipeServiceModel> recipeServiceModels = new ArrayList<>();
        Mockito.when(recipeServiceMock.findAllRecipes()).thenReturn(recipeServiceModels);
        ScheduleReport scheduleReport = new ScheduleReport();
        scheduleReport.setRecipeService(recipeServiceMock);

        // act
        scheduleReport.makeReport();

        // verify
        Mockito.verify(recipeServiceMock, Mockito.times(1)).findAllRecipes();
    }
}