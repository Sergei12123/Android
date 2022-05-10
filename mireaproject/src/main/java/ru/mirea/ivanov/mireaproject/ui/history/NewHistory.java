package ru.mirea.ivanov.mireaproject.ui.history;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.ivanov.mireaproject.R;
import ru.mirea.ivanov.mireaproject.ui.history.dao.History;
import ru.mirea.ivanov.mireaproject.ui.historyDelay.HistoryModelShared;

public class NewHistory extends AppCompatActivity {

    private EditText editText;

    private HistoryModelShared modelShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_history);
        modelShared = new ViewModelProvider(this).get(HistoryModelShared.class);
        editText = findViewById(R.id.editTextNewHistory);
    }

    public void onClickSave(View view){
        History history = new History();
        history.story = editText.getText().toString();
        modelShared.createHistory(history,0);
        this.finish();
    }
}