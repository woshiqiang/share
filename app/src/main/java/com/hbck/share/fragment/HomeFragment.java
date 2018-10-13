package com.hbck.share.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hbck.share.R;
import com.hbck.share.adapter.HomeQuestionPagerAdapter;

/**
 * @author
 * @time 2018-10-12 10:04
 * @类描述：首页
 * @变更记录:
 */

public class HomeFragment extends Fragment {

    private TabLayout homeTabLayout;
    private ViewPager homeViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        homeTabLayout = (TabLayout) view.findViewById(R.id.homeTabLayout);
        homeViewPager = (ViewPager) view.findViewById(R.id.homeViewPager);

        homeViewPager.setAdapter(new HomeQuestionPagerAdapter(getActivity().getSupportFragmentManager(), getActivity()));
        homeTabLayout.setupWithViewPager(homeViewPager);
    }
}
