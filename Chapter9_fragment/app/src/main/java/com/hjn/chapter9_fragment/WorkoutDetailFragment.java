package com.hjn.chapter9_fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
        // 保存workoutId
        savedInstanceState.putLong("workoutId", workoutId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            // 设置workoutId的值
            workoutId = savedInstanceState.getLong("workoutId");
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
