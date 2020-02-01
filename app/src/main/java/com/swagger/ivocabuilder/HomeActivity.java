package com.swagger.ivocabuilder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Date;


public class HomeActivity extends AppCompatActivity{

   private ViewPager viewPager ;
   private CustomPagerAdaper customPagerAdaper;
   private TabLayout tabLayout;
   private WebView webView;
   TextView textView;
   TextView textView2;
   ProgressDialog progressDialog;
   String meaningFromsite;
   String text;
   String text2;
   String finalmeaning;

    String urlformeaning= "https://dictionary.cambridge.org/dictionary/english/";
    String urlend="?s=t";
    String urlforsentence="https://sentence.yourdictionary.com/";


   int id=1;
    String word;
    String meaning;
    String explanation;
    String wordtext;

    WordsViewModel viewModel;

    private EditText wordbar;
    private EditText meaningbar;
    private EditText explabar;
    private int currentpage;

    private WordsViewModel mWordViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*Intent intent = new Intent(getApplicationContext(),ClipboardMonitorService.class);
        startService(intent);*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("iVocabuilder");



        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        customPagerAdaper = new CustomPagerAdaper(getSupportFragmentManager());
        viewPager.setAdapter(customPagerAdaper);
        tabLayout.setupWithViewPager(viewPager);

        viewModel = ViewModelProviders.of(this).get(WordsViewModel.class);

           String data = getIntent().getStringExtra("copiedLink");
           if(data != null && !data.isEmpty()){
               openDialog(data);
           }


        FloatingActionButton fab = findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openDialog("");
           }
       });

    }

    private void openDialog(String text) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.layout_dialog, null);



        builder1.setTitle("Enter Word:");
        wordbar = view.findViewById(R.id.word);
        meaningbar = view.findViewById(R.id.meaning);
        explabar = view.findViewById(R.id.explanation);
        textView=view.findViewById(R.id.seemeningid);
        textView2=view.findViewById(R.id.seesentenceid);



        //wordbar.setText(text);



        builder1.setView(view);
        builder1.setMessage("Enter Your Word.");
        builder1.setCancelable(true);

        //find for Sentence from html parser

            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    word =wordbar.getText().toString();

                    if (word.isEmpty())
                    {
                        Toast.makeText(HomeActivity.this,"Please eneter your word",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        GetSentence getSentence;
                        getSentence = new GetSentence();
                        getSentence.execute();
                    }

                }
            });

            //find for meaning from html parser

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    word =wordbar.getText().toString();

                    if(word.isEmpty())
                    {
                        Toast.makeText(HomeActivity.this,"Please enter your word",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        GetMeaning getMeaning;
                        getMeaning = new GetMeaning();
                        getMeaning.execute();
                    }

                    }

            });




        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        word = wordbar.getText().toString();
                        meaning = meaningbar.getText().toString();
                        explanation = explabar.getText().toString();


                        if (word.equals("") && meaning.equals("")){
                            Toast.makeText(HomeActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            Data data = new Data();
                            data.setWord(word);
                            data.setMeaning(meaning);
                            data.setExplanation(explanation);
                            data.setDate(new Date());
                            viewModel.insert(data);
                            wordbar.setText("");
                            meaningbar.setText("");
                            explabar.setText("");

                        }
                     }
                });


        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        wordbar.setText("");
                        meaningbar.setText("");
                        explabar.setText("");
                        dialog.cancel();


                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;

    }


    private class GetSentence extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(HomeActivity.this);
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


/*

                Element lll=document.getElementsByClass("one-click-content css-1p89gle e1q3nk1v4").first();
                Element llll=document.getElementsByClass("luna-example italic").first();
                //meaningFromsite=String.valueOf(lll.text()).trim();

                text=String.valueOf(lll.text());
                text2=String.valueOf(llll.text());

                int a=text.length();
                int b=text2.length();
                int c=abs(a-b);
                if(a>b)
                {
                    finalmeaning=String.valueOf(lll.text()).substring(0,c);
                }
*/






            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }




    private class GetMeaning extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(HomeActivity.this);
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


/*

                Element lll=document.getElementsByClass("one-click-content css-1p89gle e1q3nk1v4").first();
                Element llll=document.getElementsByClass("luna-example italic").first();
                //meaningFromsite=String.valueOf(lll.text()).trim();

                text=String.valueOf(lll.text());
                text2=String.valueOf(llll.text());

                int a=text.length();
                int b=text2.length();
                int c=abs(a-b);
                if(a>b)
                {
                    finalmeaning=String.valueOf(lll.text()).substring(0,c);
                }
*/


                //finalmeaning=String.valueOf(lll.text()).trim();




            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.all_users) {



        }
        if (item.getItemId() == R.id.graph) {
            Intent intent = new Intent(getApplicationContext(),LineChartActivity.class);
            startActivity(intent);

        }

        return true;
    }
}
