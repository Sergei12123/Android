package ru.mirea.ivanov.task7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button okBtn = findViewById(R.id.btnOk);
        TextView txtView = findViewById(R.id.textView);

        View.OnClickListener btnOkClicked = view -> txtView.setText("OK button pressed");
        okBtn.setOnClickListener(btnOkClicked);
        //т.к. OnClickListener - это интерфейс с одним методом onClick, можем использовать лямбду
    }

    public void onCancelClick(View view){
        this.<TextView>findViewById(R.id.textView).setText("CANCEL button pressed");
    }
}