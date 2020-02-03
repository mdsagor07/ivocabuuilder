package com.swagger.ivocabuilder;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class LineChartActivity extends AppCompatActivity  {
LineChart  chart;
    Toolbar toolbar;
    WordsViewModel mWordsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Learning Statistics Graph");

        mWordsViewModel = ViewModelProviders.of(this).get(WordsViewModel.class);

     mWordsViewModel.getCount().observe(this, new Observer<Integer>() {
         @Override
         public void onChanged(Integer integer) {
             chart = findViewById(R.id.chart);
             chart.setScaleEnabled(false);
             ArrayList<Entry> yValues = new ArrayList<>();
             float f = (float) integer;

             yValues.add(new Entry(0,0));
             yValues.add(new Entry(1,f));
             yValues.add(new Entry(2,0f));
             yValues.add(new Entry(3,0f));
             yValues.add(new Entry(4,0f));
             yValues.add(new Entry(5,0f));
             yValues.add(new Entry(6,0f));
             yValues.add(new Entry(7,0f));
             yValues.add(new Entry(8,0f));
             yValues.add(new Entry(9,0f));
             yValues.add(new Entry(10,0f));
             yValues.add(new Entry(11,0f));
             yValues.add(new Entry(12,0f));

             LineDataSet dataSet = new LineDataSet(yValues,"Vocabulary Performance");
             ArrayList<ILineDataSet> dataSets = new ArrayList<>();
             dataSets.add(dataSet);
             LineData lineData = new LineData(dataSets);
             chart.setData(lineData);
         }
     });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
