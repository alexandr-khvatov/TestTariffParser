package com.example.testtariffparser.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumWebDriverConfig {
    @Bean
    public WebDriver firefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        var driver = WebDriverManager.firefoxdriver().remoteAddress("http://selenium:4444/wd/hub").create();
        var options = new FirefoxOptions();
        options.addArguments("-headless");
        return driver;
    }
}

