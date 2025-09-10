package com.xqy.java_sms_cloud_ai.tool.weather.function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class FunctionConfig {

    @Bean
    @Description("获取指定城市的天气")
    public Function<WeatherFunction.WeatherRequest, String> weatherFunction() {
        return new WeatherFunction();
    }
}
