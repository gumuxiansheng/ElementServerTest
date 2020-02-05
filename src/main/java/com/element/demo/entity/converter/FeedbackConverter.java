package com.element.demo.entity.converter;

import java.io.IOException;
import java.util.HashMap;

import com.element.demo.entity.FeedbackEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import tech.tablesaw.api.Row;

public class FeedbackConverter {

    private static class FeedbackConverterHolder {
        public static FeedbackConverter instance = new FeedbackConverter();
    }

    private FeedbackConverter() {
        ClassPathResource classPathResource = new ClassPathResource("feedback_properties_map.json");
        String propertiesJsonStr;
        try {
            propertiesJsonStr = FileUtils.readFileToString(classPathResource.getFile(), "UTF-8");
            propertyMaps = new Gson().fromJson(propertiesJsonStr, new TypeToken<HashMap<String, HashMap<String, String>>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static FeedbackConverter getInstance() {
        return FeedbackConverterHolder.instance;
    }

    static HashMap<String, HashMap<String, String>> propertyMaps = null;

    public FeedbackEntity getEntity(String schema, Row row, String fileName) {
        HashMap<String, String> propertyMap = propertyMaps.get(schema);

        FeedbackEntity feedbackEntity = new FeedbackEntity();

        for (String property : propertyMap.keySet()) {
            String rowName = propertyMap.get(property);
            feedbackEntity.setProperty(property, row.getObject(rowName));
        }

        feedbackEntity.setProvince("北京");
        feedbackEntity.setCity("北京市");
        feedbackEntity.setStatus("待处理");
        feedbackEntity.setFileName(fileName);

        return feedbackEntity;
    }
}