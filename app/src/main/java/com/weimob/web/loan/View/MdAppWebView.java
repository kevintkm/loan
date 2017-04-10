package com.weimob.web.loan.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by chris on 15/10/29.
 */
public class MdAppWebView extends WebView {

    private Context context = null;

    /**
     * ************************************************************************
     * data
     * *************************************************************************
     */
    private String originalUserAgent = null;
    private ScrollInterface mScrollInterface;

    /**
     * ************************************************************************
     * Web Listener
     * *************************************************************************
     */
    public interface OnWebListener {
        public void onWebCallback(String param);
    }

    private OnWebListener webListener = null;

    /**
     * ************************************************************************
     * Initialize
     * *************************************************************************
     */
    public MdAppWebView(Context context) {
        this(context, null);
    }

    public MdAppWebView(Context context, AttributeSet attrs) {
        /**
         * Strange!!!
         *
         * The 3rd parameter that only support >= 21 (5.0),
         * but we use here at low-level version still work,
         * no warning & no crash.
         *
         * We should more care here
         *
         * By Chris.yang@weimob.com
         */
        super(context, attrs,0);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MdAppWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOnWebListener(OnWebListener listener) {
        webListener = listener;
    }

    private void init(Context context) {
        this.context = context;
        originalUserAgent = getSettings().getUserAgentString();
        initWebSettings();
    }



    /**
     * *************************************** WebView Settings ****************************************
     */
    private void initWebSettings() {
        WebSettings webSettings = this.getSettings();
        // JSEngine Enable
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);

        // 默认使用缓存
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // 设置可以使用localStorage
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        // 应用可以有缓存
        webSettings.setAppCacheEnabled(true);
        String appCaceDir = context.getApplicationContext()
                .getDir("cache", Context.MODE_PRIVATE)
                .getPath();
        webSettings.setAppCachePath(appCaceDir);

        // 可以读取文件缓存(manifest生效)
        webSettings.setAllowFileAccess(true);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollInterface != null) {
            mScrollInterface.onSChanged(l, t, oldl, oldt);
        }
    }

    public void setOnCustomScroolChangeListener(ScrollInterface scrollInterface) {
        this.mScrollInterface = scrollInterface;
    }

    public interface ScrollInterface {
        public void onSChanged(int l, int t, int oldl, int oldt);
    }


    public final void callWebListener(String param) {
        if (webListener != null) {
            webListener.onWebCallback(param);
        }
    }

    public final void resetUserAgent() {
        if (!"".equals(originalUserAgent)&&originalUserAgent != null) {
            getSettings().setUserAgentString(originalUserAgent);
        }
    }
}
