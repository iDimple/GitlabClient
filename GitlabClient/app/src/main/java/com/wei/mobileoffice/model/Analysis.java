package com.wei.mobileoffice.model;

import java.util.List;

/**
 * Created by Alisa on 17/6/25.
 */

public class Analysis {
    private int studentId;
    private int assignmentId;
    List<QuestionResults> questionResults;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public List<QuestionResults> getQuestionResults() {
        return questionResults;
    }

    public void setQuestionResults(List<QuestionResults> questionResults) {
        this.questionResults = questionResults;
    }
}
