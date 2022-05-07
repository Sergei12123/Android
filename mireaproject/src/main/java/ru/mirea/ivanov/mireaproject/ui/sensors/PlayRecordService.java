package ru.mirea.ivanov.mireaproject.ui.sensors;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import ru.mirea.ivanov.mireaproject.R;

public class PlayRecordService extends Service {
    private MediaPlayer mediaPlayer;

    public PlayRecordService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        mediaPlayer=MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
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