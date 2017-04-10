package com.weimob.web.loan;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.weimob.web.loan.IWebView.IWebViewClient;
import com.weimob.web.loan.Utils.ResultUtils;
import com.weimob.web.loan.Utils.Util;
import com.weimob.web.loan.View.MdAppWebView;

import static com.weimob.web.loan.R.id.webProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private MdAppWebView webView;
    private View mColorView;
    private View back;
    private TextView mTitle;
    public static final String url = "http://m.mengxiaodai.cn/vd/mxiaodai/index";
    public static final String searchUrl = "http://m.mengxiaodai.cn/vd/mxiaodai/loanSearch";
    public static final String myLoan = "http://m.mengxiaodai.cn/vd/mxiaodai/myLoan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setContentViewRes(R.layout.activity_main, "#00000000");
        } else {
            setContentView(R.layout.activity_main);
        }

        initUi();
        initWebChromeClient();
        setWebViewClient();
        willingLoadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            back();
            if (webView.canGoBack()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void willingLoadUrl(String url) {
        if (!Util.isEmpty(url)) {
            if (url.indexOf("http") == 0 || url.indexOf("https") == 0) {
                // do nothing
            } else if (url.indexOf("file") == 0) {
                webView.getSettings().setDefaultTextEncodingName("UTF-8");
            }
            webView.loadUrl(url);
        }
    }

    private void setWebViewClient() {
        webView.setWebViewClient(new IWebViewClient() {
            @Override
            public void pageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void pageFinished(WebView view, String url) {
                mTitle.setText(view.getTitle());
                if (isHomePage(url)) {
                    back.setVisibility(View.GONE);
                } else {
                    back.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public boolean needOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.length() > 0 && url.startsWith("mdapp")) {
                    ResultUtils.processResult(MainActivity.this, url);
                }
                return false;
            }
        });
    }

    private boolean isHomePage(String currentUrl) {
        if (url.equals(currentUrl) || searchUrl.equals(currentUrl) || myLoan.equals(currentUrl)) {
            return true;
        }
        return false;
    }

    private void initWebChromeClient() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (progressBar != null) {
                    if (newProgress == 100) {
                        progressBar.setProgress(newProgress);
                        progressBar.setVisibility(View.GONE);
                        progressBar.setProgress(0);
                    } else {
                        if (progressBar.getVisibility() == View.GONE)
                            progressBar.setVisibility(View.VISIBLE);
                        if (progressBar.getProgress() < newProgress) {
                            progressBar.setProgress(newProgress);
                        }
                    }
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

    private void initUi() {
        webView = (MdAppWebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(webProgressBar);
        back = findViewById(R.id.back);
        mTitle = (TextView) findViewById(R.id.topTitle);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    private void back() {
        String currentUrl = webView.getUrl();
        if (isHomePage(currentUrl)) {
            finish();
        } else {
            webView.goBack();
        }
    }

    private void setContentViewRes(int layoutResID, String color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            /**
             * 沉浸式与虚拟按键有冲突  By chris.yang
             */
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        LinearLayout parent = new LinearLayout(this);
        parent.setOrientation(LinearLayout.VERTICAL);
        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mColorView = new View(this);
        mColorView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Util.getStatusBarHeight(this)));
        mColorView.setBackgroundColor(Color.parseColor(color));
        parent.addView(mColorView);
        View view = View.inflate(this, layoutResID, null);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        parent.addView(view);
        setContentView(parent);
    }

}
