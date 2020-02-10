package com.element.demo.service;

import java.util.List;

import com.element.demo.dao.QueryMap;
import com.element.demo.entity.FeedbackEntity;

import org.springframework.web.multipart.MultipartFile;

public interface FeedbackService {

    public int uploadFile(MultipartFile file);

    public int updateFile(MultipartFile file);

    public int update(FeedbackEntity source);

    public int update(Long id, String property, Object value);

    public List<FeedbackEntity> queryAll();

    public List<FeedbackEntity> queryFileRecords(String fileName);

    public FeedbackEntity query(Long id);

    public List<FeedbackEntity> query(List<Long> ids);

    public List<FeedbackEntity> query(QueryMap qm);

    public int distribute(boolean immediately);

    public int distribute(Long id);

    public int distribute(List<Long> id);

    public int treat(Long id, String treatmentStatus, String treatment);

    public int treat(List<FeedbackEntity> feedbackEntity);

    public boolean delete(FeedbackEntity feedbackEntity);

    public String getSchemas();
}