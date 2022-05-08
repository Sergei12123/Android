package ru.mirea.ivanov.notebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText editFileName, editText;

    private SharedPreferences preferences;

    final String SAVED_TEXT = "saved_text";

    final String SAVED_FILE_NAME = "saved_file_name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editFileName = findViewById(R.id.editFileName);
        editText = findViewById(R.id.editText);
        preferences = getPreferences(MODE_PRIVATE);

        String text = preferences.getString(SAVED_TEXT, "Empty");
        if(!text.equals("Empty"))
            editText.setText(text);
        String fileName = preferences.getString(SAVED_FILE_NAME, "Empty");
        if(!fileName.equals("Empty"))
            editFileName.setText(fileName);
    }

    public void onSaveText(View view) {
        FileOutputStream outputStream;
        String fileName = editFileName.getText().toString();
        String text = editText.getText().toString();

        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences.Editor editor = preferences.edit();
        // Сохранение значения по ключу SAVED_TEXT
        editor.putString(SAVED_TEXT, editText.getText().toString());
        editor.putString(SAVED_FILE_NAME, editFileName.getText().toString());
        editor.apply();
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }
}