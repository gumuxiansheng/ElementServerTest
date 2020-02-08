package com.element.demo.config;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "com.element.demo.feedback")
public class FeedbackConfig{
    @Getter @Setter int distributeTimeInterval;
    @Getter @Setter String distributeTaskInterval;

    public String getSchemas(){
        ClassPathResource classPathResource = new ClassPathResource("feedback_properties_map.json");
        String propertiesJsonStr = "";
        try {
            propertiesJsonStr = FileUtils.readFileToString(classPathResource.getFile(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertiesJsonStr;
    }

}