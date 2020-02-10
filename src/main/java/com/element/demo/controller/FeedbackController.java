package com.element.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.element.demo.config.Config;
import com.element.demo.config.FeedbackConfig;
import com.element.demo.dao.QueryMap;
import com.element.demo.entity.FeedbackEntity;
import com.element.demo.service.impl.FeedbackServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/feedback")
@EnableScheduling
public class FeedbackController {

    @Autowired
    FeedbackConfig feedbackConfig;

    private FeedbackServiceImpl feebackService = new FeedbackServiceImpl();

    /**
     * Index page
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "Greetings from Feedback Spring Boot!" + feedbackConfig.getDistributeTimeInterval();
    }

    /**
     * Upload excel file
     * @param file excel file from request body, csv not supported yet
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @CrossOrigin
    public List<FeedbackEntity> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        boolean override = Boolean.valueOf(request.getParameter("override"));
        if (override) {
            feebackService.updateFile(file);
        } else {
            feebackService.uploadFile(file);
        }
        
        return feebackService.queryFileRecords(file.getOriginalFilename());
        // return "Uploaded table file! " + insertedNum + " items inserted";
    }

    @RequestMapping(value = "/uploadTest", method = RequestMethod.POST)
    @CrossOrigin
    public List<FeedbackEntity> uploadFileTest(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return feebackService.queryFileRecords(file.getOriginalFilename());
        // return feebackService.uploadFileTest(file);
    }

    @RequestMapping(value = "/distribute", method = RequestMethod.POST)
    @CrossOrigin
    public List<FeedbackEntity> distribute(@RequestParam(value="ids", required = true) List<Long> ids, HttpServletRequest request) {
        int distributedNum = feebackService.distribute(ids);
        Logger.getGlobal().info("distributedNum" + distributedNum);
        return feebackService.query(ids);
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @CrossOrigin
    public List<FeedbackEntity> query(@RequestBody QueryMap qMap, HttpServletRequest request) {
        return feebackService.query(qMap);
    }

    /**
     * List out all feedbacks in the database
     * @return json data
     */
    @RequestMapping(value = "/list_all.json", produces="application/json;charset=utf-8")
    @CrossOrigin
    public List<FeedbackEntity> listData() {
        // return json
        return feebackService.queryAll();
    }

    /**
     * List out all feedbacks in the database
     * @return json data
     */
    @RequestMapping(value = "/schema_config.json", produces="application/json;charset=utf-8")
    @CrossOrigin
    public String getSchemaConfig() {
        // return json
        return new Config().getFeedbackSchemas();
    }

}