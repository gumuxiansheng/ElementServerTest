package com.element.demo.entity.converter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.element.demo.config.Config;
import com.element.demo.config.FeedbackEnumerate;
import com.element.demo.config.FeedbackSchema;
import com.element.demo.entity.FeedbackEntity;
import com.element.demo.entity.FeedbackSummary;
import com.element.demo.entity.FeedbackSummary.CategorySummaryItem;
import com.element.demo.entity.FeedbackSummary.FeedbackColumnSummary;
import com.element.demo.util.LocalDateAdapter;
import com.element.table.JsonHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

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

    public FeedbackSummary getSummary(String schemaName, List<FeedbackEntity> result) {
        FeedbackSummary summary = new FeedbackSummary();

        HashMap<String, FeedbackSchema> sSchemaMap = new Config().getFeedbackSchema(schemaName);

        Table qTable = JsonHandler.readJson(new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create().toJson(result));
        List<String> colNames = qTable.columnNames();

        for (String colName : colNames) {
            if (!sSchemaMap.containsKey(colName)){
                continue;
            }
            String sType = sSchemaMap.get(colName).getSummaryType();
            if ("Time".equals(sType)) {
                // TODO: Table scolTable = qTable.categoricalColumn(colName).countByCategory();
            } else if ("Category".equals(sType)) {
                Table scolTable = qTable.categoricalColumn(colName).countByCategory();
                List<CategorySummaryItem> summaryItems = new Gson().fromJson(JsonHandler.writeJson(scolTable), new TypeToken<ArrayList<CategorySummaryItem>>(){}.getType());
                summary.addColumnSummary(new FeedbackColumnSummary(colName, summaryItems));
            } else {

            }
        }
        return summary;
    }
}