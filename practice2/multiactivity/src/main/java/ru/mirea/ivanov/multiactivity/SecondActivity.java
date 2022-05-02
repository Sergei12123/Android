package ru.mirea.ivanov.multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import ru.mirea.ivanov.multiactivity.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySecondBinding binding;

    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume2");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause2");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop2");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy2");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart2");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate2");
        setContentView(R.layout.activity_second);
        ((TextView)findViewById(R.id.textView)).setText((String) getIntent().getSerializableExtra("key"));
    }

    public void onClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key", "Возвращаемся на первую активити");
        startActivity(intent);
    }
}