package com.example.convergecodelab.view;

import android.webkit.WebView;
import android.webkit.WebViewClient;

class WebViewController extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
