package com.element.demo.entity.converter;

import java.util.HashMap;

import com.element.demo.entity.FeedbackEntity;

import tech.tablesaw.api.Row;

public class FeedbackConverter {
    public static FeedbackEntity getEntity(HashMap<String, String> propertyMap, Row row, String fileName){
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