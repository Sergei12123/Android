package ru.mirea.ivanov.mireaproject.ui.historyDelay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.ivanov.mireaproject.R;
import ru.mirea.ivanov.mireaproject.databinding.FragmentNewHistoryDelayBinding;
import ru.mirea.ivanov.mireaproject.ui.history.dao.History;

public class NewHistoryDelayFragment extends Fragment {

    private FragmentNewHistoryDelayBinding binding;

    private HistoryModelShared modelShared;

    private EditText editHistory, editDelay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewHistoryDelayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        modelShared = new ViewModelProvider(this).get(HistoryModelShared.class);
        editDelay = root.findViewById(R.id.editDelay);
        editHistory = root.findViewById(R.id.editStory);
        root.findViewById(R.id.buttonCreateDelayStory).setOnClickListener(this::onClick);
        // Inflate the layout for this fragment
        return root;
    }

    public void onClick(View view){
        History history = new History();
        history.story = editHistory.getText().toString();
        if(editDelay.getText().toString().equals("")) editDelay.setText("0");
        modelShared.createHistory(history, Integer.parseInt(editDelay.getText().toString()));
    }
}