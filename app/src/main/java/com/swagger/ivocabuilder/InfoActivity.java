package com.swagger.ivocabuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
  TextView word,meaning,expla,date;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        word = findViewById(R.id.word_info);
        meaning = findViewById(R.id.meaning_info);
        expla = findViewById(R.id.explanation_info);
        date = findViewById(R.id.date);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Details");



        String stword,stmeaning,explanation,datee;

        stword = getIntent().getStringExtra("word");
        stmeaning = getIntent().getStringExtra("meaning");
        explanation = getIntent().getStringExtra("exp");
        datee = getIntent().getStringExtra("date");

        word.setText(stword);
        meaning.setText(stmeaning);
        expla.setText(explanation);
        date.setText(datee);


    }
}
