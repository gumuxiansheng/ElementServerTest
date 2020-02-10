package com.element.demo.config;

import javax.annotation.PostConstruct;

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

    public int getFeedbackDistributeTimeInterval(){
        return config.feedbackConfig.getDistributeTimeInterval();
    }

    public String getFeedbackDistributeTaskInterval(){
        return config.feedbackConfig.getDistributeTaskInterval();
    }

    public int getFeedbackQueryLimitCount(){
        return config.feedbackConfig.getQueryLimitCount();
    }

}