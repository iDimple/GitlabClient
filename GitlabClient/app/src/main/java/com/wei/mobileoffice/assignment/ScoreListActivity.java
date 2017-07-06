package com.wei.mobileoffice.assignment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wei.mobileoffice.BaseActivity;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.model.Students;

import java.util.HashMap;

public class ScoreListActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private TextView description;
    private TextView startAt;
    private TextView endAt;
    private HashMap<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();
    private Students students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);
        students = (Students) getIntent().getSerializableExtra("detailScore");
        initView();
    }


    private void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("分数列表");

        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        startAt = (TextView) findViewById(R.id.startAt);
        endAt = (TextView) findViewById(R.id.endAt);

        getInfo();
    }

    public void onClick(View v) {

    }


    private void getInfo() {
        title.setText(students.getStudentName());
        description.setText(students.getStudentNumber());
        startAt.setText(students.getScore() + "");
        if (students.isScored()) {
            endAt.setText("true");
        } else {
            endAt.setText("falseliu");
        }
    }


}
