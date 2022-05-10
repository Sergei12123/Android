package ru.mirea.ivanov.mireaproject.ui.historyDelay;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.ivanov.mireaproject.ui.history.dao.History;

public class HistoryModelShared extends ViewModel {
    private static MutableLiveData<History> historyMutableLiveData = new MutableLiveData<>();
    private static MutableLiveData<Boolean> isReady = new MutableLiveData<>();

    public void createHistory(History history,int delay) {
        isReady.postValue(false);
        new Handler().postDelayed(() -> {
            historyMutableLiveData.postValue(history);
            isReady.postValue(true);
        }, delay*1000);
    }

    public MutableLiveData<Boolean> getReadyState() {
        return isReady;
    }

    public MutableLiveData<History> getHistory() {
        isReady.postValue(false);
        return historyMutableLiveData;
    }
}
