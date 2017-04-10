package com.weimob.web.loan.IWebView;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by chris on 15/9/14.
 */
public abstract class IWebViewClient extends WebViewClient {

    public abstract void pageStarted(WebView view, String url, Bitmap favicon);
    public abstract void pageFinished(WebView view, String url);
    public abstract boolean needOverrideUrlLoading(WebView view, String url);

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        pageFinished(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        pageStarted(view, url, favicon);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(needOverrideUrlLoading(view, url)) {
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }
}
