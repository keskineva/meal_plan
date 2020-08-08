package softuni.meal_plan.web;

import com.bethecoder.ascii_table.ASCIITable;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.meal_plan.model.service.PlannedMealServiceModel;
import softuni.meal_plan.model.service.RecipeIngredientServiceModel;
import softuni.meal_plan.service.PlannedMealService;
import softuni.meal_plan.service.RecipeIngredientService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/orders")
public class OrderController extends BaseController {
    private Logger logger = Logger.getLogger(OrderController.class.getName());
    private final RecipeIngredientService recipeIngredientService;
    private final ModelMapper modelMapper;
    private final PlannedMealService plannedMealService;


    public OrderController(RecipeIngredientService recipeIngredientService, ModelMapper modelMapper, PlannedMealService plannedMealService) {
        this.recipeIngredientService = recipeIngredientService;
        this.modelMapper = modelMapper;
        this.plannedMealService = plannedMealService;
    }

    @GetMapping(value = "/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createOrder(ModelAndView modelAndView) {
        return super.view("order/create-order", modelAndView);
    }

    @PostMapping(value = "/create", params = {"order"})
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createOrderConfirm(ModelAndView modelAndView) {
        Map<String, Integer> totalIngredientsAndAmountsMap = getTotalIngredientsMap();
        //print in html
        modelAndView.addObject("totalIngredientsAndAmountsMap", totalIngredientsAndAmountsMap);
        return super.view("order/create-order", modelAndView);
    }

    private Map<String, Integer> getTotalIngredientsMap() {
        //1. take all planned meals
        List<PlannedMealServiceModel> allPlannedMeals = plannedMealService.findAllPlannedMealsByUsername();
        Map<String, Integer> totalIngredientsAndAmountsMap = new LinkedHashMap<>();
        //2. foreach plannedmeal: plannedmeal.portions*
        for (PlannedMealServiceModel onePlannedMeal : allPlannedMeals) {
            int desiredPortions = onePlannedMeal.getPlannedPortionsCount();
            int portionsInOneRecipe = onePlannedMeal.getRecipe().getPortionsCount();
            //3. planned meal.recipe
            String recipeId = onePlannedMeal.getRecipe().getId();
            //4. recipe_ingredients -> ingredients and amounts
            List<RecipeIngredientServiceModel> ingredientsAmounts =
                    recipeIngredientService.findIngredientsAndAmounts(recipeId);

            for (RecipeIngredientServiceModel ingredientAmount : ingredientsAmounts) {
                String ingredientName = ingredientAmount.getIngredient().getName();
                int totalAmountNeeded = (ingredientAmount.getAmount() / portionsInOneRecipe) * desiredPortions;
                //add to Map
                if (totalIngredientsAndAmountsMap.containsKey(ingredientName)) {
                    int oldAmount = totalIngredientsAndAmountsMap.get(ingredientName);
                    totalIngredientsAndAmountsMap.replace(ingredientName, oldAmount + totalAmountNeeded);
                } else {
                    totalIngredientsAndAmountsMap.put(ingredientName, totalAmountNeeded);
                }
            }

        }
        return totalIngredientsAndAmountsMap;
    }

    @GetMapping(value = "/shoppingList")
    @PreAuthorize("isAuthenticated()")
    public void generateShoppingListTxt(HttpServletResponse response) {
        String [] header = { "Bought?", "Ingredient", "Amount in grams or count" };
        Map<String, Integer> totalIngredientsAndAmountsMap = getTotalIngredientsMap();
        String[][] data = mapToMatrix(totalIngredientsAndAmountsMap);
        String table = ASCIITable.getInstance().getTable(header, data);

        try {
            // get your file as InputStream
            InputStream is = new ByteArrayInputStream(table.getBytes());
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.setContentType("text/plain");
            response.addHeader("Content-Disposition", String.format("attachment; filename=ShoppingList_%s.txt", new Date()));
            response.flushBuffer();
        } catch (IOException ex) {
            logger.info("Error writing file to output stream: " + ex.getMessage());
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    private String[][] mapToMatrix(Map<String, Integer> totalIngredientsAndAmountsMap) {
        String[][] data = {};
        data = new String[totalIngredientsAndAmountsMap.size()][];
        int index = 0;
        for (Map.Entry<String, Integer> oneIngredientPair : totalIngredientsAndAmountsMap.entrySet()) {
            data[index] = new String[] {"[ ]", oneIngredientPair.getKey(), oneIngredientPair.getValue().toString()};
            index++;
        }
        return data;
    }
}