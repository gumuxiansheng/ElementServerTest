package com.element.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.element.demo.config.Config;
import com.element.demo.config.FeedbackConfig;
import com.element.demo.config.FeedbackEnumerate;
import com.element.demo.dao.QueryMap;
import com.element.demo.entity.FeedbackEntity;
import com.element.demo.entity.FeedbackSummary;
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
        boolean override = request.getParameterMap().keySet().contains("override") ? Boolean.valueOf(request.getParameter("override")) : true;
        if (override) {
            feebackService.updateFile(file);
        } else {
            feebackService.uploadFile(file);
        }
        
        return feebackService.queryFileRecords(file.getOriginalFilename());
    }

    @RequestMapping(value = "/uploadTest", method = RequestMethod.POST)
    @CrossOrigin
    public List<FeedbackEntity> uploadFileTest(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return feebackService.queryFileRecords(file.getOriginalFilename());
    }

    @RequestMapping(value = "/distribute", method = RequestMethod.POST)
    @CrossOrigin
    public List<FeedbackEntity> distribute(@RequestBody List<Long> ids, HttpServletRequest request) {
        int distributedNum = feebackService.distribute(ids);
        Logger.getGlobal().info("distributedNum: " + distributedNum);
        return feebackService.query(ids);
    }

    @RequestMapping(value = "/treat", method = RequestMethod.POST)
    @CrossOrigin
    public List<FeedbackEntity> treat(@RequestBody List<FeedbackEntity> feedbackEntities, HttpServletRequest request) {
        feebackService.treat(feedbackEntities);
        List<Long> ids = new ArrayList<>();
        for (FeedbackEntity fEntity : feedbackEntities) {
            ids.add(fEntity.getId());
        }
        return feebackService.query(ids);
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @CrossOrigin
    public List<FeedbackEntity> query(@RequestBody QueryMap qMap, HttpServletRequest request) {
        return feebackService.query(qMap);
    }

    @RequestMapping(value = "/querySummary", method = RequestMethod.POST)
    @CrossOrigin
    public FeedbackSummary querySummary(@RequestBody QueryMap qMap, HttpServletRequest request) {
        String schemaName = request.getParameterMap().keySet().contains("schema") ? request.getParameter("schema") : "schema1";
        return feebackService.querySummery(qMap, schemaName);
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

    @RequestMapping(value = "/enumerate.json", produces="application/json;charset=utf-8")
    @CrossOrigin
    public FeedbackEnumerate getEnumerates() {
        return new Config().getFeedbackEnumerates();
    }

}