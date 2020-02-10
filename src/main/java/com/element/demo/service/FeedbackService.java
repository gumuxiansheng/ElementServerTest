package com.element.demo.service;

import java.util.List;

import com.element.demo.dao.QueryMap;
import com.element.demo.entity.FeedbackEntity;

import org.springframework.web.multipart.MultipartFile;

public interface FeedbackService {

    public int uploadFile(MultipartFile file);

    public int updateFile(MultipartFile file);

    public int updateFeedback(FeedbackEntity source, String property, Object value);

    public List<FeedbackEntity> listAll();

    public int distribute(boolean immediately);

    public int distribute(Long id);

    public int distribute(List<Long> id);

    public FeedbackEntity query(Long id);

    public List<FeedbackEntity> query(List<Long> ids);

    public List<FeedbackEntity> query(QueryMap qm);

    public boolean add(FeedbackEntity feedbackEntity);

    public boolean update(FeedbackEntity feedbackEntity);

    public boolean delete(FeedbackEntity feedbackEntity);

    public String getSchemas();
}