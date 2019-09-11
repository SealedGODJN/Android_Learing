package com.hjn.chapter9_fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class StopwatchFragment extends Fragment {

    // Number of seconds displayed on the Stopwatch
    private int seconds = 0;
    // Is the stopwatch running?
    private boolean running;
    // to make sure that whether the stopwatch is running or not
    private boolean wasRunning;

    public StopwatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if(wasRunning) {
            running = true;
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            // 如果活动是重新创建的，则恢复wasRunning的状态
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        /*
         * 不能在onCreate()方法中调用runTimer()，因为调用onCreate()方法的时候还没有设置布局
        View view = getView();
        // 更新秒表的方法
        runTimer(view);
         */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // View layout = getView();——错误及其原因：片段还没有变成视图，得通过inflater充气,片段才能变成视图
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        // 更新秒表的方法
        runTimer(layout);

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_stopwatch, container, false);
        // 前面已经获取了相应的视图变量，不用再重复一遍，直接输出对应的视图变量
        return layout;
    }

    // Start the stopwatch running when the Start Button is clicked.
    public void onClickStart(View view){
        running = true;
    }

    // Stop the stopwatch running when the Stop Button is clicked.
    public void onClickStop(View view){
        running = false;
    }

    // Reset the stopwatch running when the Reset Button is clicked.
    public void onClickReset(View view){
        running = false;
        seconds = 0;
    }

    // Sets the number of seconds on the stopwatch.
    private void runTimer(View view) {// 此方法的访问权限为private，而不是public

        // 得到文本视图
        final TextView timeView = (TextView)view.findViewById(R.id.time_view);

        // 创建一个新的Handler（用来调度要在将来某个时间点运行的代码）
        final Handler handler = new Handler();
        // post()方法会立即处理代码
        handler.post(new Runnable() {
            @Override
            public void run() {
                // 将seconds格式化为小时：分钟：秒数
                int hours = seconds/3600;
                int minutes = (seconds % 3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs);

                // 在TextView中设置文本“time”，显示时间
                timeView.setText(time);
                if(running) {
                    seconds++;
                }
                // 在1000ms后再次提交并运行Runnable中的代码
                handler.postDelayed(this,1000);
            }
        });
    }

}
