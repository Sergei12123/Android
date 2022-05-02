package ru.mirea.ivanov.toastapp;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast toast = Toast.makeText(getApplicationContext(), "Здравствуй MIREA! ИВАНОВ СЕРГЕЙ АЛЕКСАНДРОВИЧ", Toast.LENGTH_LONG);

        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        ImageView ImageView = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ImageView.setLayoutParams(linLayoutParam);
        ImageView.setImageResource(R.drawable.image1);
        ImageView.setScaleType(android.widget.ImageView.ScaleType.CENTER_INSIDE);
        ImageView.setAdjustViewBounds(true);
        toastContainer.addView(ImageView, 0);
        toast.show();
    }
}