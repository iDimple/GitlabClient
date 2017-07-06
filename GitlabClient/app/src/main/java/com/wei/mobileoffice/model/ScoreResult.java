package com.wei.mobileoffice.model;

/**
 * Created by Alisa on 17/6/25.
 */

public class ScoreResult {
    private String git_url;
    private int score;
    private boolean scored;

    public String getGit_url() {
        return git_url;
    }

    public void setGit_url(String git_url) {
        this.git_url = git_url;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }
}
