package com.element.demo.service;

import java.util.List;

import com.element.demo.entity.FeedbackEntity;

import org.springframework.web.multipart.MultipartFile;

public interface FeedbackService {

    public int uploadFile(MultipartFile file);

    public int updateFile(MultipartFile file);

    public int updateFeedback(FeedbackEntity source, String property, Object value);

    public List<FeedbackEntity> listAll();

    public boolean distribute();

    public List<FeedbackEntity> query();

    public boolean add(FeedbackEntity feedbackEntity);

    public boolean update(FeedbackEntity feedbackEntity);

    public boolean delete(FeedbackEntity feedbackEntity);
}