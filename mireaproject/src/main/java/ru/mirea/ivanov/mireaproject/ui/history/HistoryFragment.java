package ru.mirea.ivanov.mireaproject.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.mirea.ivanov.mireaproject.App;
import ru.mirea.ivanov.mireaproject.R;
import ru.mirea.ivanov.mireaproject.databinding.FragmentHistoryBinding;
import ru.mirea.ivanov.mireaproject.ui.history.dao.AppDatabase;
import ru.mirea.ivanov.mireaproject.ui.history.dao.History;
import ru.mirea.ivanov.mireaproject.ui.history.dao.HistoryDao;
import ru.mirea.ivanov.mireaproject.ui.historyDelay.HistoryModelShared;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView = root.findViewById(R.id.historyList);
        updateUi();
        HistoryModelShared modelShared = new ViewModelProvider(this).get(HistoryModelShared.class);
        modelShared.getReadyState().observe(getViewLifecycleOwner(),isReady->{
            if(isReady){
                AppDatabase db = App.getInstance().getDatabase();
                HistoryDao historyDao = db.historyDao();
                historyDao.insert(modelShared.getHistory().getValue());
                updateUi();
            }
        });
        return root;
    }

    private void updateUi(){
        AppDatabase db = App.getInstance().getDatabase();
        HistoryDao historyDao = db.historyDao();
        List<History> historyList = historyDao.getAll();

        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
        for (History history : historyList) {
            HashMap<String, Object> historyCollect = new HashMap<>();
            historyCollect.put("ID", history.id);
            historyCollect.put("History", history.story);
            arrayList.add(historyCollect);
        }

        SimpleAdapter mHistory =
                new SimpleAdapter(getContext(), arrayList, android.R.layout.simple_list_item_2,
                        new String[]{"ID", "History"},
                        new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(mHistory);
    }


}