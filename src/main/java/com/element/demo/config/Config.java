package com.element.demo.config;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Config {

    @Autowired
    private FeedbackConfig feedbackConfig;

    public static Config config;

    @PostConstruct
    public void init() {
        config = this;
        config.feedbackConfig = this.feedbackConfig;
    }

    public String getFeedbackSchemas(){
        return config.feedbackConfig.getSchemas();
    }

    public HashMap<String, FeedbackSchema> getFeedbackSchema(String schemaName){
        HashMap<String, HashMap<String, FeedbackSchema>> sHashMap = new Gson().fromJson(config.feedbackConfig.getSchemas(), new TypeToken<HashMap<String, HashMap<String, FeedbackSchema>>>() {}.getType());
        if (sHashMap == null) {
            return new HashMap<>();
        }
        return sHashMap.get(schemaName);
    }

    public int getFeedbackDistributeTimeInterval(){
        return config.feedbackConfig.getDistributeTimeInterval();
    }

    public String getFeedbackDistributeTaskInterval(){
        return config.feedbackConfig.getDistributeTaskInterval();
    }

    public int getFeedbackQueryLimitCount(){
        return config.feedbackConfig.getQueryLimitCount();
    }

    public FeedbackEnumerate getFeedbackEnumerates(){
        return config.feedbackConfig.getEnumerates();
    }

}