package com.xqy.java_sms_cloud_ai.tool.weather.tool;


import org.springframework.ai.tool.annotation.Tool;


public class WeatherToolImpl implements WeatherTool {

    @Override
    @Tool(description = "获取指定城市天气")
    public String getWeather(String city) {
        return city + "天气是晴天~";
    }
}
