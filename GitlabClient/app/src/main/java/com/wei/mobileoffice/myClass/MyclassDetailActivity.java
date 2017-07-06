package com.wei.mobileoffice.myClass;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wei.mobileoffice.BaseActivity;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.model.Student;



public class MyclassDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView sId;
    private TextView nickTitle;
    private TextView name;
    private TextView className;
    private TextView gender;
    private TextView email;
    private TextView school;
    private TextView gitUsername;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("customdetail");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail);
        student = (Student) getIntent().getSerializableExtra("theStudent");
        System.out.println("student" + student.getGitUsername());
         initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("学生详情");

        name = (TextView) findViewById(R.id.name);
        nickTitle = (TextView) findViewById(R.id.nick_title);
        sId = (TextView) findViewById(R.id.sId);
        className = (TextView) findViewById(R.id.className);
        gender = (TextView) findViewById(R.id.gender);
        email = (TextView) findViewById(R.id.email);
        school = (TextView) findViewById(R.id.school);
        gitUsername = (TextView) findViewById(R.id.gitUsername);


        findViewById(R.id.title_bar_back).setOnClickListener(this);
        setInfo();
    }


    private void setInfo() {
        sId.setText(student.getNumber()+"");
        nickTitle.setText(student.getUsername()+"");
        name.setText(student.getName()+"");
        className.setText(student.getGroupId()+"");
        gender.setText(student.getGender()+"");
        email.setText(student.getEmail()+"");
        //school.setText();
        gitUsername.setText(student.getGitId()+"");

    }

    @Override
    public void onClick(View v) {

    }

}
