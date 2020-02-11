package com.element.demo.entity.converter;

import java.util.HashMap;
import java.util.List;

import com.element.demo.config.Config;
import com.element.demo.config.FeedbackEnumerate;
import com.element.demo.config.FeedbackSchema;
import com.element.demo.entity.FeedbackEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tech.tablesaw.api.Row;

public class FeedbackConverter {

    private static class FeedbackConverterHolder {
        public static FeedbackConverter instance = new FeedbackConverter();
    }

    private FeedbackConverter() {
        String propertiesJsonStr = new Config().getFeedbackSchemas();
        propertyMaps = new Gson().fromJson(propertiesJsonStr,
                new TypeToken<HashMap<String, HashMap<String, FeedbackSchema>>>() {
                }.getType());
    }

    public static FeedbackConverter getInstance() {
        return FeedbackConverterHolder.instance;
    }

    static HashMap<String, HashMap<String, FeedbackSchema>> propertyMaps = new HashMap<>();

    /**
     * Get FeedbackEntity instance from tablesaw row and it's source file name.
     * 
     * @param schema   file schema configured in
     *                 resources/feedback_properties_map.json
     * @param row      tablesaw row, one row represents one entity
     * @param fileName conversion file's name, need to record it in database
     * @return the instance of FeedbackEntity
     */
    public FeedbackEntity getFiledEntity(String schema, Row row, String fileName) {
        HashMap<String, FeedbackSchema> propertyMap = propertyMaps.get(schema);

        FeedbackEntity feedbackEntity = new FeedbackEntity();
        List<String> colNames = row.columnNames();

        if (colNames == null) {
            return feedbackEntity;
        }

        for (String property : propertyMap.keySet()) {
            String colName = propertyMap.get(property).getName();

            if (colNames.contains(colName)) {
                feedbackEntity.setProperty(property, row.getObject(colName));
            }

        }

        // Default values
        feedbackEntity.setProvince("北京");
        feedbackEntity.setCity("北京市");
        feedbackEntity.setStatus("待处理");
        feedbackEntity.setFileName(fileName);

        return feedbackEntity;
    }

    public boolean validate(FeedbackEntity fEntity){
        FeedbackEnumerate fEnumerate = new Config().getFeedbackEnumerates();
        if (!fEnumerate.getTreatmentStatus().contains(fEntity.getTreatmentStatus())){
            return false;
        }
        if (!fEnumerate.getStatus().contains(fEntity.getStatus())){
            return false;
        }

        return true;
    }
}