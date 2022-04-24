package ru.mirea.ivanov.intentapp;

import static ru.mirea.ivanov.intentapp.Constants.EXTRA_MESS_NAME;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSendBtnClicked(View view) {
        long dateInMillis = System.currentTimeMillis();
        String format = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String dateString = dateFormat.format(new Date(dateInMillis));


        Intent secondActivity = new Intent(
                this,
                SecondActivity.class
        );
        secondActivity.putExtra(EXTRA_MESS_NAME.getValue(), dateString);
        startActivity(secondActivity);
    }
}