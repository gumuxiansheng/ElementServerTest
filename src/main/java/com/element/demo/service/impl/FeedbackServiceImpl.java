package com.element.demo.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
                FeedbackEntity fEntity = FeedbackConverter.getInstance().getEntity("schema1", row, fileName);
                insertedNum += session.insert("insertFeedback", fEntity);
            }
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return insertedNum;
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

    @Override
    public boolean distribute() {
        // TODO Auto-generated method stub
        return false;
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

}