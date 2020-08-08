package softuni.meal_plan.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import softuni.meal_plan.model.service.RecipeServiceModel;
import softuni.meal_plan.service.RecipeService;

import java.util.List;
import java.util.logging.Logger;

public class ScheduleReport {

    private final Logger logger = Logger.getLogger(ScheduleReport.class.getName());
    public ScheduleReport() {
    }

    @Autowired
    private RecipeService recipeService;

    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // seconds minutes hours days months year
    // http://www.cronmaker.com/
    @Scheduled(cron="*/30 * * * * ?")
    public void makeReport()
    {
        List<RecipeServiceModel> allRecipes = recipeService.findAllRecipes();
        if (null != allRecipes) {
            logger.info(String.format("Scheduled Report: Current number of recipes: %s", allRecipes.size()));
        }
    }
}
