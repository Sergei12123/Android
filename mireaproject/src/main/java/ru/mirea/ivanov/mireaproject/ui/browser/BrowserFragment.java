package ru.mirea.ivanov.mireaproject.ui.browser;

import static ru.mirea.ivanov.mireaproject.MainActivity.preferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;

import ru.mirea.ivanov.mireaproject.R;
import ru.mirea.ivanov.mireaproject.databinding.FragmentBrowserBinding;

public class BrowserFragment extends Fragment {

    private FragmentBrowserBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBrowserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String url = preferences.getString("SAVED_ADDRESS", "https://developer.android.com");
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            url = "https://developer.android.com";
        }
        WebView wv = root.findViewById(R.id.webview);
        wv.setWebViewClient(new BrowserViewModel());
        wv.loadUrl(url);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}