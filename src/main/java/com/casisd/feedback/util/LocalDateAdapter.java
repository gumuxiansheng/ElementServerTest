package com.casisd.feedback.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class LocalDateAdapter implements JsonSerializer<LocalDateTime> {

    public static final String GLOBALTIMEFORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public JsonElement serialize(final LocalDateTime localDateTime, final Type type, final JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern(GLOBALTIMEFORMAT)));
    }
}