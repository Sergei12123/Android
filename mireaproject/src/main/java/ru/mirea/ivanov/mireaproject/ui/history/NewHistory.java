package ru.mirea.ivanov.mireaproject.ui.history;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.ivanov.mireaproject.App;
import ru.mirea.ivanov.mireaproject.R;
import ru.mirea.ivanov.mireaproject.ui.history.dao.AppDatabase;
import ru.mirea.ivanov.mireaproject.ui.history.dao.History;
import ru.mirea.ivanov.mireaproject.ui.history.dao.HistoryDao;

public class NewHistory extends AppCompatActivity {

    private EditText editText;

    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_history);

        editText = findViewById(R.id.editTextNewHistory);
    }

    public void onClickSave(View view){
        AppDatabase db = App.getInstance().getDatabase();
        HistoryDao historyDao = db.historyDao();
        History history = new History();
        history.story = editText.getText().toString();
        historyDao.insert(history);
        this.finish();
    }
}