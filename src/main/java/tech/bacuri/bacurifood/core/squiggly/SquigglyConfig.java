package tech.bacuri.bacurifood.core.squiggly;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SquigglyConfig {

    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper mappers) {
        Squiggly.init(mappers, new RequestSquigglyContextProvider("campos", null));
        List<String> urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");
        FilterRegistrationBean<SquigglyRequestFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new SquigglyRequestFilter());
        filterRegistration.setUrlPatterns(urlPatterns);
        filterRegistration.setOrder(1);
        return filterRegistration;
    }
}
