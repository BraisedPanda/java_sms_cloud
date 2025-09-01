package com.xqy.java_sms_cloud_user_provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class JavaSmsCloudUserProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaSmsCloudUserProviderApplication.class, args);
    }

}
