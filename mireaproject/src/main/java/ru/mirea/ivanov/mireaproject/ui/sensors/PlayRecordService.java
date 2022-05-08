package ru.mirea.ivanov.mireaproject.ui.sensors;

import static ru.mirea.ivanov.mireaproject.MainActivity.preferences;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class PlayRecordService extends Service {
    private MediaPlayer mediaPlayer;
    private int id;

    public PlayRecordService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer!=null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setLooping(preferences.getBoolean("SAVED_LOOPING_AUDIO", false));
            try {
                mediaPlayer.setDataSource(intent.getStringExtra("path"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        }
        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }

    public void onPause() {
        mediaPlayer.pause();
    }
}