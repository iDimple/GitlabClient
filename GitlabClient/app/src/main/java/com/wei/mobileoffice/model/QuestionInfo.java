package com.wei.mobileoffice.model;

import java.io.Serializable;

/**
 * Created by Alisa on 17/6/25.
 */

public class QuestionInfo implements Serializable {

    private int id;
    private String title;
    private String description;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
