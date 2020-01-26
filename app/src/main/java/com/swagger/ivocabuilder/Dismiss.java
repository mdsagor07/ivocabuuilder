package com.swagger.ivocabuilder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class Dismiss extends AppCompatActivity {


    int id = 1;

    String word;
    String meaning;
    String explanation;

    WordsViewModel viewModel;

    private EditText wordbar;
    private EditText meaningbar;
    private EditText explabar;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CharSequence text = getIntent()
                .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);

        viewModel = ViewModelProviders.of(this).get(WordsViewModel.class);

        String data = getIntent().getStringExtra("copiedLink");

        int len=data.length();
        int count=0;

        if(data.contains(" ")){
            count++;
        }


        if (data != null && !data.isEmpty() && count==0){
            openDialog(data);
        }
        else {

            forSenencDialog(data);
        }

    }

    private void forSenencDialog(String data) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.dismis_dialoge, null);


        builder1.setTitle("Enter Word:");
        wordbar = view.findViewById(R.id.word1);
        meaningbar = view.findViewById(R.id.meaning2);
        explabar = view.findViewById(R.id.explanation3);

       /* textView=view.findViewById(R.id.seemeningid);
        textView.setVisibility(View.INVISIBLE);*/



        explabar.setText(data);

        builder1.setView(view);
        builder1.setMessage("Enter Your Word.");
        builder1.setCancelable(true);

        /*textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(), GoogleTranslate.class);

                startActivity(intent);
            }
        });*/

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        word = wordbar.getText().toString();
                        meaning = meaningbar.getText().toString();
                        explanation = explabar.getText().toString();

                        if (word.equals("") && meaning.equals("")) {
                            Toast.makeText(getApplicationContext(), "Fields Are Empty", Toast.LENGTH_SHORT).show();
                        }  else {
                            Data data = new Data();
                            data.setWord(word);
                            data.setMeaning(meaning);
                            data.setExplanation(explanation);
                            data.setDate(new Date());
                            viewModel.insert(data);
                            finish();

                        }

                    }

                });


        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }

    private void openDialog(String data) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.layout_dialog, null);


        builder1.setTitle("Enter Word:");
        wordbar = view.findViewById(R.id.word);
        meaningbar = view.findViewById(R.id.meaning);
        explabar = view.findViewById(R.id.explanation);

        wordbar.setText(data);

        builder1.setView(view);
        builder1.setMessage("Enter Your Word.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        word = wordbar.getText().toString();
                        meaning = meaningbar.getText().toString();
                        explanation = explabar.getText().toString();

                        if (word.equals("") && meaning.equals("")) {
                            Toast.makeText(getApplicationContext(), "Fields Are Empty", Toast.LENGTH_SHORT).show();
                        }  else {
                            Data data = new Data();
                            data.setWord(word);
                            data.setMeaning(meaning);
                            data.setExplanation(explanation);
                            data.setDate(new Date());
                            viewModel.insert(data);
                            finish();

                        }

                    }

                });


        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.setCanceledOnTouchOutside(true);
        alert11.show();


    }
}

