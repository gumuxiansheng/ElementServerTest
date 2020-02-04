package com.element.demo.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class FeedbackEntity {

    // 提交时间
    private LocalDateTime time;
    // 问卷类型
    private String qaType;
    // 来源
    private String source;
    // 涉及大厅类型
    private String institutionType;
    // 所属省
    private String province;
    // 所属市
    private String city;
    // 所属区
    private String district;
    // 内容类型
    private String contentType;
    // 反馈内容
    private String content;
    // 联系方式
    private String contact;
    // 状态
    private String status;
    // 是否已下发
    private Integer distributed;
    // 下发时间
    private LocalDateTime distributTime;
    // 处理意见
    private String treatment;
    // 上传文件名
    private String fileName;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getQaType() {
        return qaType;
    }

    public void setQaType(String qaType) {
        this.qaType = qaType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDistributed() {
        return distributed;
    }

    public void setDistributed(Integer distributed) {
        this.distributed = distributed;
    }

    public LocalDateTime getDistributTime() {
        return distributTime;
    }

    public void setDistributTime(LocalDateTime distributTime) {
        this.distributTime = distributTime;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

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
