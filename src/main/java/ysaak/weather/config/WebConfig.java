package ysaak.weather.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("static/**").addResourceLocations("classpath:static/");
        registry.addResourceHandler("apple-touch-icon.png").addResourceLocations("classpath:static/apple-touch-icon.png");
        registry.addResourceHandler("favicon.ico").addResourceLocations("classpath:favicon.ico");
    }
}
