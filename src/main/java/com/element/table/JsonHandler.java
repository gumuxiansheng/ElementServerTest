package com.element.table;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;

import com.element.demo.util.LocalDateAdapter;

import tech.tablesaw.api.Table;
import tech.tablesaw.io.json.JsonReadOptions;
import tech.tablesaw.io.json.JsonWriteOptions;

public class JsonHandler {
    public static Table readJson(String json) {
        Table table = null;
        try {
            table = Table.read().usingOptions(JsonReadOptions.builderFromString(json).build());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return table;
    }

    public static String writeJson(Table table) {
        StringWriter writer = new StringWriter();
        try {
            table.write().usingOptions(JsonWriteOptions.builder(writer).build());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }
}