package com.weimob.web.loan.Utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;


import java.util.Map;


/**
 * Created by keming.tian on 2017/3/22.
 */
public class ResultUtils {

    /**
     * app协议
     */
    private final static String APP_SCHEME = "mdloankingapp";

    public static boolean processResult(final Context context, String result) {
        if (TextUtils.isEmpty(result)) {
            return false;
        }
        final Map<String, String> urlParam = AnalysisURLUtil.getUrlParam(result);
        if (result.startsWith("http") || result.startsWith("https") || result.startsWith(APP_SCHEME)) {

            /***************************************************************************************
             * mdloankingapp://md.app/open?
             * 1. 外链 link=Base64(url);
             * 2. 电话 tel=Base64(number);
             * 3. 短信 sms=Base64(number);
             ***************************************************************************************/
            if (result.startsWith(APP_SCHEME) && result.contains("open?")) {
                if (urlParam.containsKey("link")) {
                    openBrowser(context, getDecodeBase64(urlParam.get("link")));
                } else if (urlParam.containsKey("tel")) {
                    doCall(context, getDecodeBase64(urlParam.get("tel")));
                } else if (urlParam.containsKey("sms")) {
                    doSMS(context, getDecodeBase64(urlParam.get("sms")));
                }
            }
        }
        return true;
    }

    private static String getDecodeBase64(String base64) {
        String data = null;
        if (!Util.isEmpty(base64)) {
            try {
                data = new String(Base64.decode(base64, Base64.DEFAULT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    private static void openBrowser(Context context, String url) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    private static void doCall(Context context, String telephone) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telephone)));
    }

    private static void doSMS(Context context, String telephone) {
        Intent it = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + telephone));
        it.putExtra("sms_body", "");
        context.startActivity(it);
    }

}
