package com.hjn.chapter7_starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;

public class TopLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        // Create an OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(position == 0) {
                    Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);
                }
            }
        };

        // Add the listener to the list view
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }

}
