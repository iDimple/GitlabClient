package com.wei.mobileoffice.personalInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wei.mobileoffice.BaseFragment;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.model.Liuqin;
import com.wei.mobileoffice.model.Student;
import com.wei.mobileoffice.platform.StringUtil;
import com.wei.mobileoffice.util.CacheUtils;
import com.wei.mobileoffice.util.UIHelper;

import java.util.Locale;

public class StudentInfoFragment extends BaseFragment implements View.OnClickListener {


    private TextView userId;
    private TextView nickTitle;
    private TextView nickName;
    private TextView department;
    private TextView address;
    private TextView phone;
    private Student student;


    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_student_setting, container, false);

        initView();
        initData();

        return fragmentView;
    }

    @Override
    protected void doActivityCreated(Bundle savedInstanceState) {
        this.createPage(this.getClass().getSimpleName().toLowerCase(Locale.getDefault()));
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        userId = (TextView) fragmentView.findViewById(R.id.userId);
        nickTitle = (TextView) fragmentView.findViewById(R.id.nick_title);
        nickName = (TextView) fragmentView.findViewById(R.id.nickname);
        department = (TextView) fragmentView.findViewById(R.id.department);
        address = (TextView) fragmentView.findViewById(R.id.address);
        phone = (TextView) fragmentView.findViewById(R.id.communication);

        fragmentView.findViewById(R.id.rl_nick).setOnClickListener(this);
        fragmentView.findViewById(R.id.rl_communication).setOnClickListener(this);
        fragmentView.findViewById(R.id.rl_weixin).setOnClickListener(this);
        fragmentView.findViewById(R.id.rl_address).setOnClickListener(this);
        fragmentView.findViewById(R.id.logout_icon).setOnClickListener(this);
    }
   
    private void initData() {
        String result = CacheUtils.getString(context, "Studentlogin", "");
        if (!StringUtil.isBlank(result)) {
            jsonTovo(result);
            setUserInfo();
        } else {
        }
    }

    @Override
    public void onClick(View v) {

    }


    void jsonTovo(String json) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement jsonEl = parser.parse(json);
        JsonObject jsonObj = null;
        jsonObj = jsonEl.getAsJsonObject();

        student = gson.fromJson(jsonObj, Student.class);
        System.out.println(student.getEmail());
    }

    /**
     * 登录页面设置操作
     */
    private void setUserInfo() {
        UIHelper.runInMainThread(new Runnable() {
            @Override
            public void run() {

                nickTitle.setText(student.getUsername());
                userId.setText(student.getUsername());
                nickName.setText(student.getName());

                department.setText(student.getType());
                address.setText(student.getGender());
                phone.setText(student.getEmail());
            }
        });
    }
}
