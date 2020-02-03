package com.element.demo.service;

import java.util.List;

import com.element.demo.entity.FeedbackEntity;

public interface FeedbackService {

    public boolean uploadFile();

    public List<FeedbackEntity> listAll();

    public boolean distribute();

    public List<FeedbackEntity> query();

    public boolean add(FeedbackEntity feedbackEntity);

    public boolean update(FeedbackEntity feedbackEntity);

    public boolean delete(FeedbackEntity feedbackEntity);
}