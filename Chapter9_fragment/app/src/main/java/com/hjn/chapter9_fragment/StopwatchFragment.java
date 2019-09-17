package com.hjn.chapter9_fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class StopwatchFragment extends Fragment implements View.OnClickListener{

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:
                onClickStart();
                break;

            case R.id.stop_button:
                onClickStop();
                break;

            case R.id.reset_button:
                onClickReset();
                break;

        }
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

        // 将onClickListener关联到按钮
        Button startButton = (Button)layout.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        Button stopButton = (Button)layout.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        Button resetButton = (Button)layout.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_stopwatch, container, false);
        // 前面已经获取了相应的视图变量，不用再重复一遍，直接输出对应的视图变量
        return layout;
    }

    /*
     * 使用android:onClick属性时，对应的方法必须是“有void返回类型的公共方法，且方法名要与布局XML中指定的方法一致”
     * 不使用该属性时，建议将对应的方法设置为private；在片段中，也不需要View参数
    */
    // Start the stopwatch running when the Start Button is clicked.
    private void onClickStart(){
        running = true;
    }

    // Stop the stopwatch running when the Stop Button is clicked.
    private void onClickStop(){
        running = false;
    }

    // Reset the stopwatch running when the Reset Button is clicked.
    private void onClickReset(){
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

    /*
     * 问题：P450，运行APP时，点击按钮，APP会停止运行
     * 分析：onClick属性只会调用活动中的方法，而不会调用片段中的方法
     * 解决方案：
     * 1、把片段中的方法移动到在父活动中
     * 2、修改片段StopwatchFragment.java的代码，使点击按钮的活动能被片段类侦察到
     *
     * 采用的解决方案：
     * 2——原因：如果采用方案1，则意味着片段不再是自包含的。
     * （即如果我们要在另一个活动中重用该片段，则那个活动也需要包含这些onClick相关的代码）
     * 不利于代码重用
     */


}
