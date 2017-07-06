package com.wei.mobileoffice.exam;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wei.mobileoffice.BaseActivity;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.model.Creator;

//出题者
public class CreatorActivity extends BaseActivity implements View.OnClickListener {


    private TextView nickTitle;
    private TextView name;
    private TextView gender;
    private TextView email;
    private TextView school;
    private Creator creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator);
        creator = (Creator) getIntent().getSerializableExtra("detailCreator");
        System.out.println("detailCreator" + creator.getName());
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("详情");

        name = (TextView) findViewById(R.id.name);
        nickTitle = (TextView) findViewById(R.id.nick_title);
        gender = (TextView) findViewById(R.id.gender);
        email = (TextView) findViewById(R.id.email);
        school = (TextView) findViewById(R.id.school);


        findViewById(R.id.title_bar_back).setOnClickListener(this);
        setInfo();
    }


    private void setInfo() {
        nickTitle.setText(creator.getUsername() + "");
        name.setText(creator.getName() + "");
        gender.setText(creator.getGender() + "");
        email.setText(creator.getEmail() + "");
        //school.setText();

    }

    @Override
    public void onClick(View v) {

    }
}
