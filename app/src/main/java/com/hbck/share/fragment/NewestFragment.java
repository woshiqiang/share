package com.hbck.share.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.hbck.share.R;
import com.hbck.share.activity.HomePageActivity;
import com.hbck.share.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @Date 2018-10-13.
 * 最新、最热、最近
 */
public class NewestFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String ARG_PARAM_ORDER_BY = "orderBy";//排序方式  最新为：createdAt倒叙，最热为点击量倒叙：scanNumber
    private ListView listView;
    private List<AVObject> mList = new ArrayList<>();
    private ProductAdapter adapter;
    private String orderBy;//排序方式  最新为：createdAt倒叙，最热为点击量倒叙：scanNumber

    public static NewestFragment newInstance(String orderBy) {
        NewestFragment fragment = new NewestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_ORDER_BY, orderBy);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderBy = getArguments().getString(ARG_PARAM_ORDER_BY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, null);
        initView(view);
        getData();
        return view;
    }

    private void getData() {
        AVQuery<AVObject> avQuery = new AVQuery<>("Product");
        avQuery.orderByDescending(orderBy);
        avQuery.include("owner");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mList.clear();
                    mList.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new ProductAdapter(getContext(), mList);
        listView.setAdapter(adapter);
        //点击
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AVObject item = mList.get(i);
        //点击量加1
        try {
            int scanNumber = item.getInt("scanNumber") + 1;
            item.put("scanNumber", scanNumber);
            item.save();
        } catch (AVException e) {
            e.printStackTrace();
        }
        AVUser owner = item.getAVUser("owner");
        //跳转到个人主页
        Intent intent = new Intent(getContext(), HomePageActivity.class);
        intent.putExtra("owner", owner);
        startActivity(intent);
    }
}
