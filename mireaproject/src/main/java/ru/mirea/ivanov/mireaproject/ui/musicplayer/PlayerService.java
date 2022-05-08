package ru.mirea.ivanov.mireaproject.ui.musicplayer;

import static ru.mirea.ivanov.mireaproject.MainActivity.preferences;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import ru.mirea.ivanov.mireaproject.R;

public class PlayerService extends Service {

    private MediaPlayer mediaPlayer;

    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        mediaPlayer=MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setLooping(preferences.getBoolean("SAVED_LOOPING_AUDIO", false));
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            MusicPlayerFragment.playButton.setText("Play");
        } else {
            mediaPlayer.start();
            MusicPlayerFragment.playButton.setText("Pause");

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