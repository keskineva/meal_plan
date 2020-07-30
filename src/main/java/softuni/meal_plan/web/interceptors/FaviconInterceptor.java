package softuni.meal_plan.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import softuni.meal_plan.web.annotations.PageFavicon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView modelAndView) {

//        String favicon = "https://www.freepngimg.com/download/grocery/41636-2-groceries-png-image-high-quality.png";

        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        }
        else {
            if (handler instanceof HandlerMethod) {
                PageFavicon methodAnnotation = ((HandlerMethod) handler).getMethodAnnotation(PageFavicon.class);

                if (methodAnnotation != null) {
                    modelAndView.addObject("favicon", methodAnnotation.value());
                }
            }
        }
    }
}
