package com.wei.mobileoffice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alisa on 17/6/17.
 */

public class Exam implements Serializable {
    private int id;
    private String title;
    private String description;
    private String startAt;
    private String endAt;
    List<Question> questions;
    private int course;
    private String status;
    //            "newly"|          //新建态
//            "initing"|        //正在初始化
//            "initFail"|       //初始化失败
//            "initSuccess"|    //初始化成功
//            "ongoing"|        //考试正在进行
//            "timeup"|         //考试时间到
//            "analyzing"|      //正在分析结果
//            "analyzingFinish" //结果分析完毕
    private String currentTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
