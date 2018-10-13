package com.hbck.share.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.hbck.share.R;
import com.hbck.share.adapter.ProductAdapter;
import com.hbck.share.util.ImageLoaderUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @time 2018-10-13 15:44
 * @类描述：他人主页
 * @变更记录:
 */
public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private RoundedImageView riv_head;
    private TextView tv_nick;
    private TextView tv_sign;
    private ListView listView;
    private List<AVObject> mList = new ArrayList<>();
    private ProductAdapter adapter;
    private AVUser owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initView();
        initData();
        getData();
    }

    private void getData() {
        AVQuery<AVObject> query = new AVQuery<>("Product");
        query.orderByDescending("createdAt");//按发布时间倒叙
//        query.include("owner");
        query.whereEqualTo("owner", owner);
        query.findInBackground(new FindCallback<AVObject>() {
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

    private void initData() {
        owner = getIntent().getParcelableExtra("owner");
        //昵称
        String nick = owner.getString("nick");
        if (TextUtils.isEmpty(nick)) {
            tv_nick.setText(owner.getUsername());
        } else {
            tv_nick.setText(nick);
        }

        AVFile headImg = owner.getAVFile("headImg");
        if (headImg != null)
            ImageLoaderUtil.display(this, headImg.getUrl(), riv_head);

        String sign = owner.getString("sign");
        if (!TextUtils.isEmpty(sign)) {
            tv_sign.setText(sign);
        }

        adapter = new ProductAdapter(this, mList);
        listView.setAdapter(adapter);

    }

    private void initView() {
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        riv_head = (RoundedImageView) findViewById(R.id.riv_head);
        tv_nick = (TextView) findViewById(R.id.tv_nick);
        tv_sign = (TextView) findViewById(R.id.tv_sign);
        listView = (ListView) findViewById(R.id.listView);

        ib_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
        }
    }
}
