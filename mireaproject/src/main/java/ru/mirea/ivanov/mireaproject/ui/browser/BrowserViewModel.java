package ru.mirea.ivanov.mireaproject.ui.browser;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserViewModel extends WebViewClient {


    public BrowserViewModel() {
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
