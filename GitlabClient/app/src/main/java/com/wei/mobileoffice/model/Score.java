package com.wei.mobileoffice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alisa on 17/6/25.
 */

public class Score implements Serializable {
    private int assignmentId;
    List<Questions> questions;

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }
}
