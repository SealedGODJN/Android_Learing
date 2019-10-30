package com.hjn.chapter9_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        // 只有当活动是第一次创建时,新增片段
        // 活动被撤销时,不新增片段
        if(savedInstanceState == null) {
            StopwatchFragment stopwatch = new StopwatchFragment();
            // 开始片段事务，使用支持库的片段
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // 增加stopwatch,并把这个事务增加到后退堆栈
            ft.add(R.id.stopwatch_container, stopwatch);
            // 将这个事务增加到后退堆栈
            ft.addToBackStack(null);
            // TRANSIT_FRAGMENT_FADE 设置片段事务淡入淡出
            ft.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
            ft.commit();
        }
    }
}
