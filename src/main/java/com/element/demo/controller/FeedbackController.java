package com.element.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.element.demo.entity.FeedbackEntity;
import com.element.demo.service.impl.FeedbackServiceImpl;
import com.element.demo.util.LocalDateAdapter;
import com.google.gson.GsonBuilder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FeedbackController {
    private FeedbackServiceImpl feebackService = new FeedbackServiceImpl();

    /**
     * Index page
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    /**
     * Upload excel file
     * @param file excel file from request body, csv not supported yet
     * @param request
     * @return
     */
    @RequestMapping(value = "/feedback/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        int insertedNum = feebackService.uploadFile(file);
        return "Uploaded table file! " + insertedNum + " items inserted";
    }

    /**
     * List out all feedbacks in the database
     * @return json data
     */
    @RequestMapping("/feedback/listAll")
    @ResponseBody
    public String listData() {
        List<FeedbackEntity> allFeedbacks = feebackService.listAll();
        // return json string
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create().toJson(allFeedbacks);
    }

}