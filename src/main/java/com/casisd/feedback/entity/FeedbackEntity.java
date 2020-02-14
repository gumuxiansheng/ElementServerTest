package com.casisd.feedback.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import com.casisd.feedback.util.LocalDateAdapter;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class FeedbackEntity {
    // 数据库id
    @Getter @Setter private Long id;
    // 序号
    @Getter @Setter private Integer serialNum;
    // 提交时间
    @JsonFormat(pattern = LocalDateAdapter.GLOBALTIMEFORMAT)
    @Getter @Setter private LocalDateTime time;
    // 问卷类型
    @Getter @Setter private String qaType;
    // 来源
    @Getter @Setter private String source;
    // 涉及大厅类型
    @Getter @Setter private String institutionType;
    // 所属省
    @Getter @Setter private String province;
    // 所属市
    @Getter @Setter private String city;
    // 所属区
    @Getter @Setter private String district;
    // 内容类型
    @Getter @Setter private String contentType;
    // 反馈内容
    @Getter @Setter private String content;
    // 联系方式
    @Getter @Setter private String contact;
    // 版本
    @Getter @Setter private int version;
    // 状态
    @Getter @Setter private String status;
    // 是否已下发
    @Getter @Setter private Integer distributed;
    // 下发时间
    @JsonFormat(pattern = LocalDateAdapter.GLOBALTIMEFORMAT)
    @Getter @Setter private LocalDateTime distributeTime;
    // 处理状态
    @Getter @Setter private String treatmentStatus;
    // 处理意见
    @Getter @Setter private String treatment;
    // 处理时间
    @JsonFormat(pattern = LocalDateAdapter.GLOBALTIMEFORMAT)
    @Getter @Setter private LocalDateTime treatTime;
    // 经手人
    @Getter @Setter private String treatPerson;
    // 上传文件名
    @Getter @Setter private String fileName;

    public void setProperty(String property, Object value) {
        Class<?> clz = FeedbackEntity.class;
        try {
            String setMethodStr = "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
            // Contact may be stored as long in table, we want it be string.
            if (value instanceof Long){
                value = String.valueOf(value);
            }
            Method method = clz.getMethod(setMethodStr, value.getClass());
            method.invoke(this, value);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
