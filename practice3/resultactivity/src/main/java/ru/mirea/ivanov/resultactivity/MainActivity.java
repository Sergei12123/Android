package ru.mirea.ivanov.resultactivity;

import static ru.mirea.ivanov.resultactivity.Constants.EXTRA_MESS_NAME;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 143;

    private TextView txtViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtViewResult = findViewById(R.id.txtView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String university = data.getStringExtra(EXTRA_MESS_NAME.getValue());
            setUniversityTextView(university);
        }
    }

    public void dataBtnClicked(View view) {
        Intent intent = new Intent(this, DataActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void setUniversityTextView(String university) {
        txtViewResult.setText(university);
    }
}