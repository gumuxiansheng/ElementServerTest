package com.casisd.feedback.task;

import java.util.logging.Logger;

import com.casisd.feedback.service.impl.FeedbackServiceImpl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FeedbackDistributeTask {
    private FeedbackServiceImpl feebackService = new FeedbackServiceImpl();

    @Scheduled(cron="${com.casisd.feedback.feedback.distribute-task-interval}")
    private void processFeedbackAutoDistribute(){
        Logger.getGlobal().info("process distribute");
        feebackService.distribute(false);
    }

}