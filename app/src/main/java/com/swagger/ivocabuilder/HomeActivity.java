package com.swagger.ivocabuilder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;


public class HomeActivity extends AppCompatActivity{

   private ViewPager viewPager ;
   private CustomPagerAdaper customPagerAdaper;
   private TabLayout tabLayout;
   private WebView webView;
   private TextView textView;

   int id=1;
    String word;
    String meaning;
    String explanation;

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


        wordbar.setText(text);

        builder1.setView(view);
        builder1.setMessage("Enter Your Word.");
        builder1.setCancelable(true);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(), GoogleTranslate.class);

                startActivity(intent);
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
                        }
                     }
                });


        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
