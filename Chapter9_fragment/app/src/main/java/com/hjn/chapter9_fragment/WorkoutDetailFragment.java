package com.hjn.chapter9_fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutDetailFragment extends Fragment {

    private long workoutId;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 只有当片段是第一次创建时,新增片段
        // 片段被撤销时,不新增片段
        if(savedInstanceState == null) {
            StopwatchFragment stopwatch = new StopwatchFragment();
            // 开始片段事务，使用支持库的片段
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            // 增加stopwatch,并把这个事务增加到后退堆栈
            ft.add(R.id.stopwatch_container, stopwatch);
            // 将这个事务增加到后退堆栈
            ft.addToBackStack(null);
            // TRANSIT_FRAGMENT_FADE 设置片段事务淡入淡出
            ft.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
            ft.commit();
        } else {
            // 保存workoutId
            workoutId = savedInstanceState.getLong("workoutId", workoutId);
        }
    }

    // 活动使用该方法设置训练项目ID的值
    public void setWorkoutId(long id) {
        this.workoutId = id;
    }

    public WorkoutDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 得到片段的根视图，然后使用根视图得到两个文本视图
        View view = getView();
        if(view != null) {
            // 获得workoutId对应的workout项
            Workout workout = Workout.workouts[(int) workoutId];
            TextView title = (TextView)view.findViewById(R.id.textTitle);
            title.setText(workout.getName());
            TextView description = (TextView)view.findViewById(R.id.textDescription);
            description.setText(workout.getDescription());
        }
    }

}
