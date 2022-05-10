package ru.mirea.ivanov.viewmodel;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        // получение доступа к создайнной ViewModel
        ProgressViewModel viewModel =
                new ViewModelProvider(this).get(ProgressViewModel.class);

        viewModel.getProgressState().observe(this, isVisibleProgressBar -> {
            if (isVisibleProgressBar) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
        viewModel.showProgress();
    }
}