package com.element.demo.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import com.element.demo.config.Config;
import com.element.demo.entity.FeedbackEntity;
import com.element.demo.entity.converter.FeedbackConverter;
import com.element.demo.service.FeedbackService;
import com.element.table.ExcelHandler;
import com.element.table.ExcelTable;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.web.multipart.MultipartFile;

import tech.tablesaw.api.Row;

public class FeedbackServiceImpl implements FeedbackService {

    private SqlSession getSqlSession() throws IOException {
        final String mybatisConfig = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(mybatisConfig);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession();
    }

    @Override
    public int uploadFile(MultipartFile file) {
        // 文件名
        String fileName = file.getOriginalFilename();
        int insertedNum = 0;
        // 文件流
        try {
            InputStream inputStream = file.getInputStream();

            ExcelTable table = ExcelHandler.readExcel(inputStream, true);
            SqlSession session = getSqlSession();

            for (Row row : table.getTable()) {
                FeedbackEntity fEntity = FeedbackConverter.getInstance().getFiledEntity("schema1", row, fileName);
                insertedNum += session.insert("insertFeedback", fEntity);
            }
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return insertedNum;
    }

    public List<FeedbackEntity> uploadFileTest(MultipartFile file) {
        List<FeedbackEntity> fEntities = new ArrayList<>();
        ExcelTable table = null;
        // 文件流
        try {
            InputStream inputStream = file.getInputStream();
            table = ExcelHandler.readExcel(inputStream, true);

            for (Row row : table.getTable()) {
                FeedbackEntity fEntity = FeedbackConverter.getInstance().getFiledEntity("schema1", row, "");
                fEntities.add(fEntity);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fEntities;
    }

    @Override
    public int updateFile(MultipartFile file) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int updateFeedback(FeedbackEntity source, String property, Object value) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<FeedbackEntity> listAll() {
        List<FeedbackEntity> allFeedbacks = new ArrayList<>();
        try {
            SqlSession session = getSqlSession();
            allFeedbacks.addAll(session.selectList("listAllFeedbacks"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return allFeedbacks;
    }

    // @Override
    public List<FeedbackEntity> listFileRecords(String fileName) {
        List<FeedbackEntity> fileFeedbacks = new ArrayList<>();
        HashMap<String, String> map = new HashMap<String, String>();  
        map.put("fileName", fileName);
        try {
            SqlSession session = getSqlSession();
            fileFeedbacks.addAll(session.selectList("listFileFeedbacks", map));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileFeedbacks;
    }

    @Override
    public int distribute(boolean immediately) {
        int result = 0;
        HashMap<String, Object> map = new HashMap<String, Object>();  
        map.put("timeInterval", new Config().getFeedbackDistributeTimeInterval());

        try {
            SqlSession session = getSqlSession();
            if (immediately) {
                result = session.update("distribute");
            } else {
                result = session.update("autoDistribute", map);
            }
            
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<FeedbackEntity> query() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean add(FeedbackEntity feedbackEntity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean update(FeedbackEntity feedbackEntity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(FeedbackEntity feedbackEntity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getSchemas() {
        return new Config().getFeedbackSchemas();
    }

}