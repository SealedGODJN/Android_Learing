package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BeerExpert expert = new BeerExpert();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 使用activity_main.xml文件定义的界面布局
        setContentView(R.layout.activity_main);
    }

    /*
     *@version1.0
    // Called when the user clicks the button
    public void onClickFindBeer(View view) {
        // Get a reference to the TextView
        TextView brands = (TextView) findViewById(R.id.brands);
        // Get a reference  to the Spinner
        Spinner color = (Spinner) findViewById(R.id.color);
        // Get the selected item in the Spinner
        String beerType = String.valueOf(color.getSelectedItem());
        // Display the selected item
        brands.setText(beerType);
    }
    */


    /*
     *@version1.0
     *@param: View view
     */
    // Called when the user clicks the button
    public void onClickFindBeer(View view) {
        // Get a reference to the TextView
        TextView brands = (TextView) findViewById(R.id.brands);
        // Get a reference  to the Spinner
        Spinner color = (Spinner) findViewById(R.id.color);
        // Get the selected item in the Spinner
        String beerType = String.valueOf(color.getSelectedItem());
        // Get recommendations from the BeerExpert class
        // 获得啤酒品牌列表
        List<String> brandsList = expert.getBrands(beerType);
        // 使用List中的值创建一个String
        StringBuilder brandsFormatted = new StringBuilder();
        for(String brand:brandsList) {
            // 换行显示各个啤酒品牌
            brandsFormatted.append(brand).append('\n');
        }
        // 在文本视图中显示结果
        brands.setText(brandsFormatted);
    }
}
