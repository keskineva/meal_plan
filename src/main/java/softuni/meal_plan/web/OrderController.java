package softuni.meal_plan.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.meal_plan.service.OrderService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/orders")
public class OrderController extends BaseController {
    private final Logger logger = Logger.getLogger(OrderController.class.getName());
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createOrder(ModelAndView modelAndView) {
        return super.view("order/create-order", modelAndView);
    }

    @PostMapping(value = "/create", params = {"order"})
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createOrderConfirm(ModelAndView modelAndView) {
        Map<String, Integer> totalIngredientsAndAmountsMap = orderService.getTotalIngredientsMap();
        //print in html
        modelAndView.addObject("totalIngredientsAndAmountsMap", totalIngredientsAndAmountsMap);
        return super.view("order/create-order", modelAndView);
    }

    @GetMapping(value = "/shoppingList")
    @PreAuthorize("isAuthenticated()")
    public void generateShoppingListTxt(HttpServletResponse response) {
        String table = orderService.getShoppingListOnly();

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

    @GetMapping(value = "/recipePlanList")
    @PreAuthorize("isAuthenticated()")
    public void generateRecipePlanListTxt(HttpServletResponse response) {
        StringBuilder stringBuilder = orderService.getCompleteMealPlan();

        try {
            // get your file as InputStream
            InputStream is = new ByteArrayInputStream(stringBuilder.toString().getBytes());
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.setContentType("text/plain");
            response.addHeader("Content-Disposition", String.format("attachment; filename=RecipePlanList_%s.txt", new Date()));
            response.flushBuffer();
        } catch (IOException ex) {
            logger.info("Error writing file to output stream: " + ex.getMessage());
            throw new RuntimeException("IOError writing file to output stream");
        }
    }
}