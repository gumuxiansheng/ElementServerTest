package com.element.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.element.demo.entity.*;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class FileController {
    String resource = "mybatis-config.xml";

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/upload")
    public String uploadFile() {
        // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);

            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = sqlSessionFactory.openSession();
            List<FeedbackEntity> allFeedbacks = session.selectList("listAllFeedbacks");
            for (FeedbackEntity feedback : allFeedbacks) {
                System.out.println("Time:" + feedback.getTime() + ", Content:" + feedback.getContent());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Upload table file!";
    }

    @RequestMapping("/list")
    public String listData() {
        // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
        
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);

            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            // 然后根据 sqlSessionFactory 得到 session
            SqlSession session = sqlSessionFactory.openSession();
            // 最后通过 session 的 selectList() 方法调用 sql 语句 listAllFeedbacks
            List<FeedbackEntity> allFeedbacks = session.selectList("listAllFeedbacks");
            for (FeedbackEntity feedback : allFeedbacks) {
                System.out.println("Time:" + feedback.getTime() + ", Content:" + feedback.getContent());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Upload table file!";
    }
}