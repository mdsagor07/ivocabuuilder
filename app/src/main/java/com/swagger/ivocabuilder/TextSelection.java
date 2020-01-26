package com.swagger.ivocabuilder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class TextSelection extends AppCompatActivity {


    int id = 1;

    String word;
    String meaning;
    String explanation;

    WordsViewModel viewModel;

    private EditText wordbar;
    private EditText meaningbar;
    private EditText explabar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openDialog();

        Exit();


    }

    private void Exit() {
        finish();
    }


    private void openDialog() {

        CharSequence text = getIntent()
                .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        Intent intent = new Intent(getApplicationContext(), Dismiss.class);

        intent.putExtra("copiedLink", text);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);





    }
}




