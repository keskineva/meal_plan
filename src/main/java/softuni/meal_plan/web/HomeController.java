package softuni.meal_plan.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST} )
    public ModelAndView index(){

        ModelAndView modelAndView = new ModelAndView("index");

        return modelAndView;
    }

}