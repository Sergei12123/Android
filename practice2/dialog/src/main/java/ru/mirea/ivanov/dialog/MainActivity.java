package ru.mirea.ivanov.dialog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnShowDialogClicked(View view) {
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void onOkDialogClicked() {
        Toast.makeText(getApplicationContext(), "Выбрано: Иду дальше", Toast.LENGTH_LONG).show();
    }

    public void onCancelDialogClicked() {
        Toast.makeText(getApplicationContext(), "Выбрано: Нет", Toast.LENGTH_LONG).show();
    }

    public void onNeutralDialogClicked() {
        Toast.makeText(getApplicationContext(), "Выбрано: На паузе", Toast.LENGTH_LONG).show();
    }


    public void onBtnTimePickerClicked(View view) {
        TimePickerDialogFragment timePickFr = new TimePickerDialogFragment();
        timePickFr.show(getSupportFragmentManager(), "mirea");
    }


    public void onBtnDatePickerClicked(View view) {
        DatePickerDialogFragment datePickFr = new DatePickerDialogFragment();
        datePickFr.show(getSupportFragmentManager(), "mirea");
    }

    public void onBtnProgressDialogClicked(View view) {
        progress = new ProgressDialog(this);
        progress.setTitle("Mirea");
        progress.setMessage("ProgressDialog");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setMax(1024);
        progress.setIndeterminate(true);
        progress.show();

        handler = new Handler() {
            public void handleMessage(Message msg) {
                progress.setIndeterminate(false);
                if (progress.getProgress() < progress.getMax()) {
                    progress.incrementProgressBy(50);
                    progress.incrementSecondaryProgressBy(75);
                    handler.sendEmptyMessageDelayed(0, 100);
                } else {
                    progress.dismiss();
                }
            }
        };
        handler.sendEmptyMessageDelayed(0, 2000);
    }


    public void onDialogTimeSet(int hour, int minute) {
        Toast.makeText(getApplicationContext(), "Выбрано время: " + hour + ":" + minute, Toast.LENGTH_LONG).show();
    }

    public void onDialogDateSet(int year, int month, int day) {
        Toast.makeText(getApplicationContext(), "Выбрана дата: " + year + "." + month + "." + day, Toast.LENGTH_LONG).show();
    }
}