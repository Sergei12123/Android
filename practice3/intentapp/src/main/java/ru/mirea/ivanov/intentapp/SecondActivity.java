package ru.mirea.ivanov.intentapp;

import static ru.mirea.ivanov.intentapp.Constants.EXTRA_MESS_NAME;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView mainTxtView = findViewById(R.id.textView);

        String shareMsg = getIntent().getStringExtra(EXTRA_MESS_NAME.getValue());

        mainTxtView.setText(shareMsg);
    }
}