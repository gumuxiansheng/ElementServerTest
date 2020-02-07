package com.element.demo.config;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

public class Config {
    public String getFeedbackSchemas(){
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