package softuni.meal_plan.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import softuni.meal_plan.web.interceptors.FaviconInterceptor;
import softuni.meal_plan.web.interceptors.TitleInterceptor;

@EnableScheduling
@Configuration
public class ApplicationWebConfiguration implements WebMvcConfigurer {
    private final TitleInterceptor interceptor;
    private final FaviconInterceptor faviconInterceptor;

    @Autowired
    public ApplicationWebConfiguration(TitleInterceptor interceptor, FaviconInterceptor faviconInterceptor) {
        this.interceptor = interceptor;
        this.faviconInterceptor = faviconInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.interceptor);
        registry.addInterceptor(this.faviconInterceptor);
    }
}