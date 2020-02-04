package com.element.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.element.demo.entity.FeedbackEntity;
import com.element.demo.entity.converter.FeedbackConverter;
import com.element.table.ExcelHandler;
import com.element.table.ExcelTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import tech.tablesaw.api.Row;

@RestController
public class FileController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        // 文件名
        String fileName = file.getOriginalFilename();
        // 文件流
        try {
            ClassPathResource classPathResource = new ClassPathResource("feedback_properties_map.json");
            String propertiesJsonStr = FileUtils.readFileToString(classPathResource.getFile(), "UTF-8");
            HashMap<String, HashMap<String, String>> propertyMaps = new Gson().fromJson(propertiesJsonStr, new TypeToken<HashMap<String, HashMap<String, String>>>() {}.getType());
            InputStream inputStream = file.getInputStream();

            ExcelTable table = ExcelHandler.readExcel(inputStream);
            SqlSession session = getSqlSession();
            for (Row row : table.getTable()) {
                FeedbackEntity fEntity = FeedbackConverter.getEntity(propertyMaps.get("schema1"), row, fileName);
                session.insert("insertFeedback", fEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Uploaded table file!";
    }

    @RequestMapping("/list")
    public String listData() {
        try {
            SqlSession session = getSqlSession();
            // 最后通过 session 的 selectList() 方法调用 sql 语句 listAllFeedbacks
            List<FeedbackEntity> allFeedbacks = session.selectList("listAllFeedbacks");
            for (FeedbackEntity feedback : allFeedbacks) {
                System.out.println("Time:" + feedback.getTime() + ", Content:" + feedback.getContent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Upload table file!";
    }

    private SqlSession getSqlSession() throws IOException {
        final String mybatisConfig = "mybatis-config.xml";
        // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
        InputStream inputStream = Resources.getResourceAsStream(mybatisConfig);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 然后根据 sqlSessionFactory 得到 session
        return sqlSessionFactory.openSession();
    }
}