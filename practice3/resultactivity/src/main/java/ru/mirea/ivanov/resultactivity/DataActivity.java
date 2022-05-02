package ru.mirea.ivanov.resultactivity;

import static ru.mirea.ivanov.resultactivity.Constants.EXTRA_MESS_NAME;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DataActivity extends AppCompatActivity {

    private EditText fieldEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        fieldEditText = findViewById(R.id.txtEdit);

    }

    public void sendBtnClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_MESS_NAME.getValue(), fieldEditText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}