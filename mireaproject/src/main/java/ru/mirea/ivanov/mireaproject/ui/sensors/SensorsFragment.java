package ru.mirea.ivanov.mireaproject.ui.sensors;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.mirea.ivanov.mireaproject.MainActivity;
import ru.mirea.ivanov.mireaproject.R;
import ru.mirea.ivanov.mireaproject.databinding.FragmentSensorsBinding;

public class SensorsFragment extends Fragment {

    private static final int RESULT_OK = -1;
    private FragmentSensorsBinding binding;
    private static final int REQUEST_CODE_PERMISSION = 100;

    final String TAG = MainActivity.class.getSimpleName();
    public Uri imageUri;
    public static final int CAMERA_REQUEST = 0;
    public boolean isWork = false;
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 100;
    public static SensorsFragment instance;
    public TextView textViewAccelerometer1, textViewAccelerometer2, textViewMagnetic;
    private MediaRecorder mediaRecorder;

    private String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };
    public static SensorsFragment getInstance(){
        if(instance==null) instance= new SensorsFragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSensorsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        instance=this;
        textViewAccelerometer1 = root.findViewById(R.id.textViewAccelerometer1);
        textViewAccelerometer2 = root.findViewById(R.id.textViewAccelerometer2);
        textViewMagnetic = root.findViewById(R.id.textViewMagnetic);

        // Выполняется проверка на наличие разрешений на использование камеры и запись в память
        int cameraPermissionStatus =
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        int storagePermissionStatus = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        isWork = hasPermissions(getContext(), PERMISSIONS);
        if (!isWork) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS,
                    REQUEST_CODE_PERMISSION);
        }

        // инициализация объекта MediaRecorder
        mediaRecorder = new MediaRecorder();
        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            instance.isWork = true;
        } else {
            // Выполняется запрос к пользователь на получение необходимых разрешений
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_CAMERA);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensors, container, false);
    }

    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
// производится проверка полученного результата от пользователя на запрос разрешения Camera
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
            // permission granted
            isWork = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



}