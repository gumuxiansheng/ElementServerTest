package com.element.demo.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.element.demo.config.Config;
import com.element.demo.dao.QueryMap;
import com.element.demo.entity.FeedbackEntity;
import com.element.demo.entity.FeedbackSummary;
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

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("fileName", fileName);

            int version = session.selectOne("selectFileVersion", map);
            version++;
            for (Row row : table.getTable()) {
                FeedbackEntity fEntity = FeedbackConverter.getInstance().getFiledEntity("schema1", row, fileName);
                fEntity.setVersion(version);
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
        // 文件名
        String fileName = file.getOriginalFilename();
        int insertedNum = 0;
        // 文件流
        try {
            InputStream inputStream = file.getInputStream();

            ExcelTable table = ExcelHandler.readExcel(inputStream, true);
            SqlSession session = getSqlSession();

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("fileName", fileName);
            map.put("oriVersion", 0);

            session.insert("overrideFeedback", map);
            int version = (Integer) map.get("oriVersion") + 1;
            for (Row row : table.getTable()) {
                FeedbackEntity fEntity = FeedbackConverter.getInstance().getFiledEntity("schema1", row, fileName);
                fEntity.setVersion(version);
                insertedNum += session.insert("insertFeedback", fEntity);
            }
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return insertedNum;
    }

    @Override
    public int update(FeedbackEntity source) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<FeedbackEntity> queryAll() {
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
    public List<FeedbackEntity> queryFileRecords(String fileName) {
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
    public int distribute(Long id) {
        int result = 0;
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("feedbackId", id);

        try {
            SqlSession session = getSqlSession();
            result = session.update("distributeOne", map);
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int distribute(List<Long> ids) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("feedbackIds", ids);
        int result = 0;
        try {
            SqlSession session = getSqlSession();
            result = session.update("distributeSome", map);
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public FeedbackEntity query(Long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        List<FeedbackEntity> results = query(ids);
        return (results == null || results.isEmpty()) ? null : results.get(0);
    }

    @Override
    public List<FeedbackEntity> query(List<Long> ids) {
        List<FeedbackEntity> result = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("feedbackIds", ids);

        try {
            SqlSession session = getSqlSession();
            result = session.selectList("queryFeedbacksByIds", map);
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<FeedbackEntity> query(QueryMap qMap) {
        List<FeedbackEntity> result = new ArrayList<>();

        try {
            SqlSession session = getSqlSession();
            result = session.selectList("queryFeedbacks", qMap);
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // @Override
    public FeedbackSummary querySummary(QueryMap qMap, String schemaName) {
        List<FeedbackEntity> result = query(qMap);

        FeedbackSummary summary = FeedbackConverter.getInstance().getSummary(schemaName, result);

        return summary;
    }

    @Override
    public int treat(Long id, String treatmentStatus, String treatment, String treatPerson) {
        FeedbackEntity fEntity = new FeedbackEntity();
        fEntity.setId(id);
        fEntity.setTreatmentStatus(treatmentStatus);
        fEntity.setTreatment(treatment);
        fEntity.setTreatPerson(treatPerson);
        return treat(fEntity);
    }

    @Override
    public int treat(FeedbackEntity feedbackEntity) {
        List<FeedbackEntity> fEntities = new ArrayList<>();
        fEntities.add(feedbackEntity);
        return treat(fEntities);
    }

    @Override
    public int treat(List<FeedbackEntity> feedbackEntities) {
        int result = 0;
        try {
            SqlSession session = getSqlSession();
            for (FeedbackEntity feedbackEntity : feedbackEntities) {
                if (!FeedbackConverter.getInstance().validate(feedbackEntity)) {
                    throw new IllegalArgumentException("data error");
                }
                result += session.insert("treatById", feedbackEntity);
            }
            session.commit();
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return result;
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

    @Override
    public int update(Long id, String property, Object value) {
        // TODO Auto-generated method stub
        return 0;
    }

}