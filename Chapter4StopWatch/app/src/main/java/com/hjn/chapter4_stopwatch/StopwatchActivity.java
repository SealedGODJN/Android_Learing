package com.hjn.chapter4_stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {
    // Number of seconds displayed on the Stopwatch
    private int seconds = 0;
    // Is the stopwatch running?
    private boolean running;
    // to make sure that whether the stopwatch is running or not
    private boolean wasRunning;

    @Override
    protected void onStart() {
        super.onStart();
        // 为什么不用“wasRunning = running”呢？
        // 因为running有可能是null
        // （第一次调用onCreate，onStart方法，running的值为空）
        if(wasRunning) {
            running = true;
        }
    }

    @Override
    protected void onStop(){
        // 覆盖一个活动生命周期方法时，需要调用相应的Activity超类方法。
        // 如果没有做到，则会出现异常
        super.onStop();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // 为什么此处的非生命周期方法也需要调用相应的ACtivity超类方法？否则报错
        super.onSaveInstanceState(savedInstanceState);
        // 将seconds，running和wasRunning变量的值保存到savedInstanceState
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        /*
         * 如果这个活动是从头创建的，
         * savedInstanceState = null
         * 如果这个活动是重新创建的，
         * savedInstanceState != null
         * 因为之前已经调用过onSaveInstanceState()，
         * 就会把onSaveInstanceState()使用过的Bundle对象传递给活动。
         */
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            // 如果活动是重新创建的，则恢复wasRunning的状态
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        // 更新秒表的方法
        runTimer();
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
    private void runTimer() {// 此方法的访问权限为private，而不是public

        // 得到文本视图
        final TextView timeView = (TextView)findViewById(R.id.time_view);

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
