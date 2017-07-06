package com.wei.mobileoffice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alisa on 17/6/25.
 */

public class Questions implements Serializable {
    private QuestionInfo questionInfo;
    List<Students> students;

    public QuestionInfo getQuestionInfo() {
        return questionInfo;
    }

    public void setQuestionInfo(QuestionInfo questionInfo) {
        this.questionInfo = questionInfo;
    }

    public List<Students> getStudents() {
        return students;
    }

    public void setStudents(List<Students> students) {
        this.students = students;
    }
}
