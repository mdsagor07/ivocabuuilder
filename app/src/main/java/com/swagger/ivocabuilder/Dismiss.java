package com.swagger.ivocabuilder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.ViewModelProviders;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Date;

public class Dismiss extends AppCompatActivity {


    int id = 1;

    String word;
    String meaning;
    String explanation;
    String wordtext;

    String text;
    String finalmeaning;
    String urlformeaning="https://dictionary.cambridge.org/dictionary/english/";

    String urlforsentence="https://sentence.yourdictionary.com/";


    ProgressDialog progressDialog;
    WordsViewModel viewModel;

    private EditText wordbar;
    private EditText meaningbar;
    private EditText explabar;

    TextView textView;
    TextView textView2;

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

    private void forSenencDialog(String data)
    {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.layout_dialog, null);



        wordbar = view.findViewById(R.id.word);
        meaningbar = view.findViewById(R.id.meaning);
        explabar = view.findViewById(R.id.explanation);

        textView=view.findViewById(R.id.seemeningid);
        textView.setVisibility(View.INVISIBLE);



        explabar.setText(data);

        builder1.setView(view);
        builder1.setCancelable(true);


        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        word = wordbar.getText().toString().toLowerCase().trim();
                        meaning = meaningbar.getText().toString().toLowerCase().trim();
                        explanation = explabar.getText().toString().toLowerCase().trim();

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



        wordbar = view.findViewById(R.id.word);
        meaningbar = view.findViewById(R.id.meaning);
        explabar = view.findViewById(R.id.explanation);
        textView=view.findViewById(R.id.seemeningid);
        textView2=view.findViewById(R.id.seesentenceid);

        textView.setVisibility(View.VISIBLE);
        wordbar.setText(data);
        wordtext=wordbar.getText().toString().toLowerCase().trim();

       // find for sentence from html parser from yourdictionary.com
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word =wordbar.getText().toString().toLowerCase().trim();

                if (word.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please eneter your word",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(Dismiss.this,"Please ensure your connection",Toast.LENGTH_SHORT).show();

                    GetSentence getSentence;
                    getSentence = new GetSentence();
                    getSentence.execute();
                }



            }
        });


       //  for find the meaning from dictionary.cambridge.com
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                word =wordbar.getText().toString().toLowerCase().trim();

                if(word.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please enter your word",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(Dismiss.this,"Please ensure your connection",Toast.LENGTH_SHORT).show();

                    GetMeaning getMeaning;
                    getMeaning = new GetMeaning();
                    getMeaning.execute();
                }

            }
        });

        //wordbar.setText(data);

        builder1.setView(view);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        word = wordbar.getText().toString().toLowerCase().trim();
                        meaning = meaningbar.getText().toString().toLowerCase().trim();
                        explanation = explabar.getText().toString().toLowerCase().trim();

                        if (word.equals("") && meaning.equals("")) {
                            Toast.makeText(getApplicationContext(), "Fields Are Empty", Toast.LENGTH_SHORT).show();
                        }  else {
                            Data data = new Data();
                            data.setWord(word);
                            data.setMeaning(meaning);
                            data.setExplanation(explanation);
                            data.setDate(new Date());
                            viewModel.insert(data);

                            wordbar.setText("");
                            meaningbar.setText("");
                            explabar.setText("");

                            finish();

                        }

                    }

                });


        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        wordbar.setText("");
                        meaningbar.setText("");
                        explabar.setText("");
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.setCanceledOnTouchOutside(true);
        alert11.show();


    }

    private class GetSentence extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Dismiss.this);
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Find for sentence ");
            progressDialog.show();

        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            explabar.setText(finalmeaning);
            progressDialog.dismiss();



        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document document= (Document) Jsoup.connect(urlforsentence+ word).get();

                Element lll=document.getElementsByClass("sentence component").first();

                text=String.valueOf(lll.text()).trim();
                int a=text.length();
                finalmeaning=String.valueOf(lll.text()).trim().substring(0,a-1);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class GetMeaning extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Dismiss.this);
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Find for meaning ");
            progressDialog.show();

        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            meaningbar.setText(finalmeaning);
            progressDialog.dismiss();



        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document document= (Document) Jsoup.connect(urlformeaning+ word).get();

                Element lll=document.getElementsByClass("def ddef_d db").first();

                text=String.valueOf(lll.text()).trim();
                int a=text.length();
                finalmeaning=String.valueOf(lll.text()).trim().substring(0,a-1);



            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

