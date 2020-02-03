package com.swagger.ivocabuilder;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class GraphActivity extends AppCompatActivity {
    WordsViewModel mWordsViewModel;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);


        final GraphView graph = findViewById(R.id.graph);


        mWordsViewModel = ViewModelProviders.of(this).get(WordsViewModel.class);

        // The onChanged() method fires when the observed data changes
        mWordsViewModel.getCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer intege) {


                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                        new DataPoint(0, intege),
                        new DataPoint(1, 0),
                        new DataPoint(2, 0),
                        new DataPoint(3, 0),
                        new DataPoint(4, 0),
                        new DataPoint(5, 0),
                        new DataPoint(6, 0),
                        new DataPoint(7, 0),
                        new DataPoint(8, 0),
                        new DataPoint(9, 0),
                        new DataPoint(10, 0),
                        new DataPoint(11, 0),
                        new DataPoint(12, 0),
                });
                graph.addSeries(series);
                series.setTitle("Vocabulary Process");
                series.setDrawDataPoints(true);

                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(15);
                paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
                series.setCustomPaint(paint);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

