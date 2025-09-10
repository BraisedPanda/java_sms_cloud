package com.xqy.java_sms_cloud_ai.tool.weather.function;
import java.util.function.Function;

public class WeatherFunction implements Function<WeatherFunction.WeatherRequest, String>{

    @Override
    public String apply(WeatherRequest request) {

        return request.city + "是晴天";
    }

    public static class WeatherRequest{
        private String city;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }

}
