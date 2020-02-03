package com.swagger.ivocabuilder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Date;

public class EditActivity extends AppCompatActivity {
      private EditText word,meaning,explanation;
      private Button cancel,update;
      private LiveData<Data> note;
      int id;

    EditNoteViewModel noteModel;
    WordsViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit");


        wordViewModel = ViewModelProviders.of(this).get(WordsViewModel.class);


        word = findViewById(R.id.word_edit);
        meaning = findViewById(R.id.meaning_edit);
        explanation = findViewById(R.id.explanation_edit);
        cancel = findViewById(R.id.cancel_button);
        update = findViewById(R.id.update_button);
          id = getIntent().getIntExtra("word_id",0);
        noteModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);
        note = noteModel.getNotes(id);
        note.observe(this, new Observer<Data>() {
            @Override
            public void onChanged(@Nullable Data note) {
               word.setText(note.getWord());
              meaning.setText(note.getMeaning());
           explanation.setText(note.getExplanation());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

    }

    public void update(){
        String wordText = word.getText().toString().toLowerCase().trim();
        String meaningText = meaning.getText().toString().trim();
        String explanation_text = explanation.getText().toString().trim();

        Data data = new Data();
        data.setId(id);
        data.setWord(wordText);
        data.setMeaning(meaningText);
        data.setExplanation(explanation_text);
        data.setDate(new Date());
          wordViewModel.update(data);
        finish();

    }

}
