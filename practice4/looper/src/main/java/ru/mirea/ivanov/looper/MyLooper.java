package ru.mirea.ivanov.looper;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MyLooper extends Thread {

    private int number = 0;

    Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    public void run() {
        Log.d("MyLooper", "run");
        Looper.prepare();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                final String years = msg.getData().getString("YEARS");
                Log.d("MyLooper", "Начали поток, ждем "+ years +" секунд");
                try {
                    TimeUnit.SECONDS.sleep(Integer.parseInt(years));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String sb = "Номер клика : " +
                        number +
                        "\nВозраст : " +
                        years + " лет" +
                        "\nПозиция : " +
                        msg.getData().getString("POSITION");
                Log.d("MyLooper", sb);
                number++;
            }
        };
        Looper.loop();
    }
}
