package com.element.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.element.demo.config.Config;
import com.element.demo.config.FeedbackConfig;
import com.element.demo.entity.FeedbackEntity;
import com.element.demo.service.impl.FeedbackServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
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
        feebackService.uploadFile(file);
        return feebackService.listFileRecords(file.getOriginalFilename());
        // return "Uploaded table file! " + insertedNum + " items inserted";
    }

    @RequestMapping(value = "/uploadTest", method = RequestMethod.POST)
    @CrossOrigin
    public List<FeedbackEntity> uploadFileTest(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return feebackService.listFileRecords(file.getOriginalFilename());
        // return feebackService.uploadFileTest(file);
    }

    /**
     * List out all feedbacks in the database
     * @return json data
     */
    @RequestMapping(value = "/list_all.json", produces="application/json;charset=utf-8")
    @CrossOrigin
    public List<FeedbackEntity> listData() {
        // return json
        return feebackService.listAll();
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