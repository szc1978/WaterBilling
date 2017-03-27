package org.water.billing;

import org.springframework.context.annotation.Configuration;  
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;  
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;  
  
@Configuration  
public class MvcConfig extends WebMvcConfigurerAdapter {  
  
    @Override  
    public void addViewControllers(ViewControllerRegistry registry) { 
    	registry.addViewController("/").setViewName("index"); 
        registry.addViewController("/index").setViewName("index"); 
        registry.addViewController("/top").setViewName("top");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/top").setViewName("top");
        registry.addViewController("/left").setViewName("left");
        registry.addViewController("/right").setViewName("right");
    }
} 