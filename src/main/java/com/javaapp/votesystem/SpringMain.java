package com.javaapp.votesystem;

import com.javaapp.votesystem.web.RestaurantController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            RestaurantController restaurantController = appCtx.getBean(RestaurantController.class);
            // restaurantController.getAll();
            System.out.println(restaurantController.getAllWithMenu());
        }
    }
}