package com.casisd.feedback.service;

import java.util.List;

import com.casisd.feedback.dao.QueryMap;
import com.casisd.feedback.entity.FeedbackEntity;
import com.casisd.feedback.entity.FeedbackSummary;

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

    public FeedbackSummary querySummary(QueryMap qMap, String schemaName);

    public int distribute(boolean immediately);

    public int distribute(Long id);

    public int distribute(List<Long> id);

    public int treat(Long id, String treatmentStatus, String treatment, String treatPerson);

    public int treat(FeedbackEntity feedbackEntity);

    public int treat(List<FeedbackEntity> feedbackEntities);

    public boolean delete(FeedbackEntity feedbackEntity);

    public String getSchemas();
}