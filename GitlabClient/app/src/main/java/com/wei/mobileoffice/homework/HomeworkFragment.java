package com.wei.mobileoffice.homework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wei.mobileoffice.BaseFragment;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.model.Exam;
import com.wei.mobileoffice.exam.ExamInfoActivity;
import com.wei.mobileoffice.platform.util.StringUtil;
import com.wei.mobileoffice.ui.CircleImageView;
import com.wei.mobileoffice.ui.pulltorefresh.PullToRefreshBase;
import com.wei.mobileoffice.ui.pulltorefresh.PullToRefreshListView;
import com.wei.mobileoffice.util.DataTypeChange;
import com.wei.mobileoffice.util.UIHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;

import static android.content.ContentValues.TAG;
//作业
public class HomeworkFragment extends BaseFragment {

    private String result;
    private PullToRefreshListView listview;
    private ContractListAdapter adapter;
    private List<Exam> list = new ArrayList<>();
    private boolean isStopRefresh = false;//停止刷新

    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.contract_fragment, container, false);
        initView();
        return fragmentView;
    }

    @Override
    protected void doActivityCreated(Bundle savedInstanceState) {
        this.createPage(this.getClass().getSimpleName().toLowerCase(Locale.getDefault()));
    }

    private void initView() {
        new Thread(new TaskRunnable()).start();
        fragmentView.findViewById(R.id.refresh_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new TaskRunnable()).start();
            }
        });
    }


    class TaskRunnable implements Runnable {
        void jsonTovo(String json) {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement jsonEl = parser.parse(json);
            JsonArray jsonArray = null;
            jsonArray = jsonEl.getAsJsonArray();

            Exam[] exam = gson.fromJson(jsonArray, Exam[].class);
            for (int i = 0; i < exam.length; i++) {
                list.add(exam[i]);
            }
            System.out.println(exam[0].getTitle());
        }

        @Override
        public void run() {
            synchronized (String.class) {
                byte[] tokenByte = Base64.encodeBase64(("liuqin" + ":" + 123).getBytes());
                String tokenStr = DataTypeChange.bytesSub2String(tokenByte, 0, tokenByte.length);
                String token = "Basic " + tokenStr;
                String url = "http://115.29.184.56:8090/api/course/1/homework";
                OkHttpUtils
                        .get()
                        .addHeader("Authorization", token)
                        .url(url)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {
                                showError();
                            }

                            @Override
                            public void onResponse(String response) {
                                result = response;
                                Log.i(TAG, result);

                                jsonTovo(result);


                                showSucceed();


                            }
                        });
            }
        }
    }

    private void showError() {
        UIHelper.runInMainThread(new Runnable() {
            @Override
            public void run() {
                fragmentView.findViewById(R.id.rl_contract_list).setVisibility(View.GONE);
                fragmentView.findViewById(R.id.progress).setVisibility(View.GONE);
                fragmentView.findViewById(R.id.loading_page_error).setVisibility(View.VISIBLE);
                fragmentView.findViewById(R.id.loading_page_empty).setVisibility(View.GONE);
            }
        });
    }

    private void showEmpty() {
        UIHelper.runInMainThread(new Runnable() {
            @Override
            public void run() {
                fragmentView.findViewById(R.id.rl_contract_list).setVisibility(View.GONE);
                fragmentView.findViewById(R.id.progress).setVisibility(View.GONE);
                fragmentView.findViewById(R.id.loading_page_error).setVisibility(View.GONE);
                fragmentView.findViewById(R.id.loading_page_empty).setVisibility(View.VISIBLE);
            }
        });
    }

    private void showSucceed() {
        UIHelper.runInMainThread(new Runnable() {
            @Override
            public void run() {
                if (StringUtil.isBlank(result)) {
                    return;
                }
                if (list == null || list.size() < 1) {
                    showEmpty();
                    return;
                }
                fragmentView.findViewById(R.id.rl_contract_list).setVisibility(View.VISIBLE);
                fragmentView.findViewById(R.id.progress).setVisibility(View.GONE);
                fragmentView.findViewById(R.id.loading_page_error).setVisibility(View.GONE);
                fragmentView.findViewById(R.id.loading_page_empty).setVisibility(View.GONE);
                if (listview == null) {
                    listview = (PullToRefreshListView) fragmentView.findViewById(R.id.contract_list);
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                    adapter = new ContractListAdapter(context);
                    listview.setAdapter(adapter);
                    listview.setOnItemClickListener(listViewItemClickListener);
                } else {
                    adapter.notifyDataSetChanged();
                    listview.onRefreshComplete();
                    if (isStopRefresh) {
                        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    } else {
                        listview.setMode(PullToRefreshBase.Mode.BOTH);
                    }
                }
            }
        });
    }



    private AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final int pos = position - 1;
            Exam model = list.get(pos);
            Intent intent = new Intent(getActivity(), ExamInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("detailExam", model);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };



    class ContractListAdapter extends BaseAdapter {
        private Context context;

        public ContractListAdapter(Context c) {
            context = c;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final HomeworkFragment.ContractListAdapter.OpportunityItemView itemView;
            if (convertView == null) {
                itemView = new HomeworkFragment.ContractListAdapter.OpportunityItemView();
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.opportunity_item, null);
                itemView.opportunity_status = (CircleImageView) convertView.findViewById(R.id.opportunity_status);
                itemView.opportunity_type = (TextView) convertView.findViewById(R.id.opportunity_type);
                itemView.opportunity_title = (TextView) convertView.findViewById(R.id.opportunity_title);
                convertView.setTag(itemView);
            } else {
                itemView = (HomeworkFragment.ContractListAdapter.OpportunityItemView) convertView.getTag();
            }

            Exam model = list.get(position);
            if (model == null) {
                return convertView;
            }
            itemView.opportunity_type.setText(model.getTitle());
            itemView.opportunity_title.setText(model.getDescription());


            return convertView;
        }

        private class OpportunityItemView {
            CircleImageView opportunity_status;
            TextView opportunity_type;
            TextView opportunity_title;

        }
    }
}
