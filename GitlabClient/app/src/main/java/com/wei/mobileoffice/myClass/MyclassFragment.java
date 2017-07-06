package com.wei.mobileoffice.myClass;

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
import com.wei.mobileoffice.model.Myclass;
import com.wei.mobileoffice.platform.util.StringUtil;
import com.wei.mobileoffice.ui.CircleImageView;
import com.wei.mobileoffice.ui.pulltorefresh.PullToRefreshBase;
import com.wei.mobileoffice.ui.pulltorefresh.PullToRefreshListView;
import com.wei.mobileoffice.util.CacheUtils;
import com.wei.mobileoffice.util.DataTypeChange;
import com.wei.mobileoffice.util.UIHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.MediaType;


import static android.content.ContentValues.TAG;

//我的班级
public class MyclassFragment extends BaseFragment {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String result;
    private PullToRefreshListView listview;
    private CustomerListAdapter adapter;
    private List<Myclass> list = new ArrayList<>();
    private int pageSize;//每页条数
    private boolean isStopRefresh = true;//停止刷新


    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.customer_fragment, container, false);
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

            Myclass[] myclass = gson.fromJson(jsonArray, Myclass[].class);
            for (int i = 0; i < myclass.length; i++) {
                list.add(myclass[i]);
            }
            System.out.println(myclass[0].getName());
        }

        @Override
        public void run() {
            synchronized (String.class) {
                byte[] tokenByte = Base64.encodeBase64(("liuqin" + ":" + 123).getBytes());
                String tokenStr = DataTypeChange.bytesSub2String(tokenByte, 0, tokenByte.length);
                String token = "Basic " + tokenStr;
                String url = "http://115.29.184.56:8090/api/group/";
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

                                CacheUtils.putString(context, "myclass", result);
                                jsonTovo(result);
                                pageSize = list.size();
                                if (pageSize == 0) {
                                    isStopRefresh = true;
                                }

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
                fragmentView.findViewById(R.id.rl_customer_list).setVisibility(View.GONE);
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
                fragmentView.findViewById(R.id.rl_customer_list).setVisibility(View.GONE);
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
                fragmentView.findViewById(R.id.rl_customer_list).setVisibility(View.VISIBLE);
                fragmentView.findViewById(R.id.progress).setVisibility(View.GONE);
                fragmentView.findViewById(R.id.loading_page_error).setVisibility(View.GONE);
                fragmentView.findViewById(R.id.loading_page_empty).setVisibility(View.GONE);
                if (listview == null) {
                    listview = (PullToRefreshListView) fragmentView.findViewById(R.id.customer_list);
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                    adapter = new CustomerListAdapter(context);
                    listview.setAdapter(adapter);
                    //  listview.setOnRefreshListener(listRefreshListener);
                    listview.setOnItemClickListener(listViewItemClickListener);
                } else {
                    adapter.notifyDataSetChanged();
                    listview.onRefreshComplete();
                }
            }
        });
    }


    private AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final int pos = position - 1;
            Myclass myclass = list.get(pos);
            Intent intent = new Intent(getActivity(), MyclassInfoActivity.class);
            intent.putExtra("classid", myclass.getId());
            startActivity(intent);
        }
    };


    class CustomerListAdapter extends BaseAdapter {
        private Context context;

        public CustomerListAdapter(Context c) {
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
            final CustomerItemView itemView;
            if (convertView == null) {
                itemView = new CustomerItemView();
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.customer_item, null);
                itemView.name = (TextView) convertView.findViewById(R.id.customer_name);
                // itemView.type = (TextView) convertView.findViewById(R.id.customer_type);
                itemView.head_icon = (CircleImageView) convertView.findViewById(R.id.head_icon);
                convertView.setTag(itemView);
            } else {
                itemView = (CustomerItemView) convertView.getTag();
            }
            Myclass model = list.get(position);
            if (model == null) {
                return convertView;
            }




            itemView.name.setText(model.getName());

            return convertView;
        }

        private class CustomerItemView {
            TextView type;
            TextView name;
            CircleImageView head_icon;
        }
    }
}
