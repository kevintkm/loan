package com.weimob.web.loan.IWebView;

/**
 * Created by chris on 15/9/15.
 */
public interface IWebViewLife {
    public void afterInitSetting();
    public void setWebViewClient();
    public void willLoading(String url);
}
