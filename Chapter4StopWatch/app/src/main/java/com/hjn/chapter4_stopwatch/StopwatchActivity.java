package com.hjn.chapter4_stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import java.util.Locale;
import android.os.Handler;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {
    // Number of seconds displayed on the Stopwatch
    private int seconds = 0;
    // Is the stopwatch running?
    private boolean running;

    @Override
    protected void onStop(){
        // 
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // 将seconds和running变量的值保存到savedInstanceState
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
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
