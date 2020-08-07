package softuni.meal_plan.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import softuni.meal_plan.scheduled.ScheduleReport;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ScheduleReport scheduleReport() {
        return new ScheduleReport();
    }

}
