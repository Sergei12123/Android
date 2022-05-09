package ru.mirea.ivanov.mireaproject;

import static ru.mirea.ivanov.mireaproject.ui.login.LoginActivity.mAuth;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;

import ru.mirea.ivanov.mireaproject.databinding.ActivityMainBinding;
import ru.mirea.ivanov.mireaproject.ui.history.NewHistory;
import ru.mirea.ivanov.mireaproject.ui.login.LoginActivity;
import ru.mirea.ivanov.mireaproject.ui.musicplayer.PlayerService;
import ru.mirea.ivanov.mireaproject.ui.sensors.PlayRecordService;
import ru.mirea.ivanov.mireaproject.ui.sensors.SensorsFragment;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private TextView textViewAccelerometer1, textViewAccelerometer2, textViewMagnetic;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private MediaRecorder mediaRecorder;
    private SensorsFragment sensorFragment;
    boolean isPlay = false;
    FragmentManager fragmentManager;
    private NavHostFragment navHostFragment;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PERMISSION = 100;
    private File audioFile;
    private String audioPath;
    private Uri audioUri;
    private MediaPlayer mediaPlayer;
    private long numberOfRecord;

    public static SharedPreferences preferences;

    final String SAVED_LOOPING_AUDIO = "SAVED_LOOPING_AUDIO";

    final String SAVED_ADDRESS = "SAVED_ADDRESS";

    final String SAVED_CALCULATE_WRONG = "SAVED_CALCULATE_WRONG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getPreferences(MODE_PRIVATE);

        mediaRecorder = new MediaRecorder();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sensorFragment = SensorsFragment.getInstance();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        setSupportActionBar(binding.appBarMain.toolbar);


        binding.appBarMain.fab.setOnClickListener(view -> startActivity(new Intent(this, NewHistory.class)));

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_browser,
                R.id.nav_calculator,
                R.id.nav_music_player,
                R.id.nav_sensors,
                R.id.nav_settings,
                R.id.nav_weather,
                R.id.nav_history)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void onClickPlayMusic(View view) {

        startService(
                new Intent(MainActivity.this, PlayerService.class));
        if (isPlay)
            ((Button) findViewById(R.id.buttonPlay)).setText("Play");
        else
            ((Button) findViewById(R.id.buttonPlay)).setText("Pause");

        isPlay = !isPlay;
    }

    public void onClickStopMusic(View view) {
        stopService(
                new Intent(MainActivity.this, PlayerService.class));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (findViewById(R.id.textViewAccelerometer1) != null) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float valueAzimuth = event.values[0];
                float valuePitch = event.values[1];
                ((TextView) findViewById(R.id.textViewAccelerometer1)).setText("Azimuth: " + valueAzimuth);
                ((TextView) findViewById(R.id.textViewAccelerometer2)).setText("Pitch: " + valuePitch);
            }
            ((TextView) findViewById(R.id.textViewMagnetic)).setText(String.format("%s : %s",
                    sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD).getName(),
                    sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD).getMaximumRange()));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onClickMakePhoto(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // проверка на наличие разрешений для камеры
        if (cameraIntent.resolveActivity(getPackageManager()) != null && sensorFragment.isWork) {
            File photoFile = null;
            try {
                photoFile = sensorFragment.createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // генерирование пути к файлу на основе authorities
            String authorities = getApplicationContext().getPackageName() + ".provider";
            sensorFragment.imageUri = FileProvider.getUriForFile(this, authorities, photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, sensorFragment.imageUri);
            startActivityForResult(cameraIntent, sensorFragment.CAMERA_REQUEST);
        }
    }

    public void openGalleryClick(View view) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            // permission granted
            sensorFragment.isWork = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    // нажатие на кнопку старт
    public void onRecordStart(View view) {
        numberOfRecord = System.currentTimeMillis();
        try {
            startRecording();
        } catch (Exception e) {
            Log.e(TAG, "Caught io exception " + e.getMessage());
        }
    }

    // нажатие на копку стоп
    public void onStopRecord(View view) {
        stopRecording();
        processAudioFile();
    }

    private void startRecording() throws IOException {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d(TAG, "sd-card success");
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            audioPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mirea" + numberOfRecord + ".3gp";
            if (audioFile == null) {
                audioFile = new File(this.getExternalFilesDir(
                        Environment.DIRECTORY_MUSIC), "mirea" + numberOfRecord + ".3gp");
            }
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(this, "Recording started!", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            Log.d(TAG, "stopRecording");
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            Toast.makeText(this, "You are not recording right now!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void processAudioFile() {
        Log.d(TAG, "processAudioFile");
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio" + numberOfRecord + audioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());
        values.put(MediaStore.Audio.Media.IS_MUSIC, true);
        ContentResolver contentResolver = getContentResolver();
        Log.d(TAG, "audioFile: " + audioFile.canRead());
        Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(baseUri, values);
        audioUri = newUri;
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
    }

    public void onClickPlayRecord(View view) {
        Intent intent = new Intent(MainActivity.this, PlayRecordService.class);
        intent.putExtra("path", audioFile.getAbsolutePath());
        startService(intent);
    }

    public void onClickLogOut(MenuItem item) {
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}