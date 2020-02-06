package com.element.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.element.demo.entity.FeedbackEntity;
import com.element.demo.service.impl.FeedbackServiceImpl;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    private FeedbackServiceImpl feebackService = new FeedbackServiceImpl();

    /**
     * Index page
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "Greetings from Feedback Spring Boot!";
    }

    /**
     * Upload excel file
     * @param file excel file from request body, csv not supported yet
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @CrossOrigin
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        int insertedNum = feebackService.uploadFile(file);
        return "Uploaded table file! " + insertedNum + " items inserted";
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

}