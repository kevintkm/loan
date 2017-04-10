package com.weimob.web.loan.Utils;

import android.content.Context;
import android.text.TextUtils;

import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析特殊的URL
 * Created by linjinfa 331710168@qq.com on 2015/4/17.
 */
public final class AnalysisURLUtil {

    /**
     * 商品Id参数名
     */
    private static final String GID_URL_PARAM = "gid";
    /**
     * Aid
     */
    private static final String AID_URL_PARAM = "aid";

    /**
     * 淘宝认证 param key
     */
    private static final String CODE_URL_PARAM = "code";
    private static final String STATE_URL_PARAM = "state";

    /**
     * 是否需要在App中跳转 参数
     */
    private static final String APPURLTYPE_URL_PARAM = "appUrlType";
    /**
     * appUrlType 对应的值  跳转商品
     */
    private static final String GOODS_VALUE = "goods";
    /**
     * appUrlType 对应的值  跳转店铺
     */
    private static final String W_SHOP_VALUE = "w_shop";

    /**
     * 解析特殊的Url  成功直接跳转
     *
     * @param context
     * @param url
     */
    public static boolean analysisUrl(Context context, String url) {
        Map<String, String> paramMap = getUrlParam(url);
        String aId = paramMap.get(AID_URL_PARAM);
        String gId = paramMap.get(GID_URL_PARAM);
        String appUrlType = paramMap.get(APPURLTYPE_URL_PARAM);

        if (!Util.isEmpty(appUrlType)) {
            if (GOODS_VALUE.equalsIgnoreCase(appUrlType) && !Util.isEmpty(gId) && !Util.isEmpty(aId)) {   //跳转商品详情
               // ProductDetailActivity.startActivityDistribution(context, aId + "_" + gId);
                return true;
            } else if (W_SHOP_VALUE.equalsIgnoreCase(appUrlType) && !Util.isEmpty(aId)) {    //跳转旺铺
                //BusinessShopActivity.startActivity(context, aId);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取Url字符串的参数
     *
     * @param url
     * @return
     */
    public static Map<String, String> getUrlParam(String url) {
        Map<String, String> paramMap = new HashMap<String, String>();
        if (Util.isEmpty(url)) {
            return paramMap;
        }
        try {
            URI uri = new URI(url);
            String params = uri.getQuery();
            if (params != null && !"".equals(params)) {
                String paramArray[] = params.split("&");
                if (paramArray != null && paramArray.length != 0) {
                    for (String pams : paramArray) {
                        String keyValuePams[] = pams.split("=");
                        if (keyValuePams != null && keyValuePams.length == 2) {
                            paramMap.put(keyValuePams[0], URLDecoder.decode(keyValuePams[1], "UTF-8"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramMap;
    }

    /**
     * 返回图片的宽高比
     */
    public static float getBitmapStaio(String url) {
        if (TextUtils.isEmpty(url)) {
            return 0;
        }
        Map<String, String> urlParam = getUrlParam(url);
        //mdw=264&mdh=220
        if (urlParam == null || urlParam.isEmpty()) {
            return 0;
        } else {
            String width = urlParam.get("mdw");
            String height = urlParam.get("mdh");
            try {
                return Float.parseFloat(height) / Float.parseFloat(width);
            } catch (Exception e) {
                return 0;
            }

        }
    }


    /**
     * 解析一键搬家中淘宝的Url  成功后获取商品列表
     *
     * @param url
     */
    public static boolean analysisThirdUrl(String url, String start, String end) {
        if (!Util.isEmpty(url)) {
            if (url.contains(start) && url.contains(end)) {   //跳转http请求
                return true;
            }
        }
        return false;
    }

    /**
     * 解析一键搬家中淘宝的Url  成功后获取商品列表
     *
     * @param url
     */
    public static boolean analysisThirdUrl(String url, String start) {
        if (!Util.isEmpty(url)) {
            if (url.contains(start)) {   //跳转http请求
                return true;
            }
        }
        return false;
    }

    /**
     * 替换域名
     *
     * @param url
     * @param newDomain
     */
    public static String replaceDomain(String url, String newDomain) {
        if (url != null && !"".equals(url) && newDomain != null && !"".equals(newDomain)) {
            try {
                URI uri = new URI(url);
                String scheme = uri.getScheme();
                int port = uri.getPort();
                String path = uri.getPath();
                String query = uri.getQuery();

                StringBuffer urlBuffer = new StringBuffer();
                urlBuffer.append(scheme != null ? (scheme + "://") : (url.startsWith("/") ? "/" : ""))
                        .append(newDomain)
                        .append((port != -1) ? (":" + port) : "")
                        .append(path != null ? path : "")
                        .append(query != null ? ("?" + query) : "");
                return urlBuffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return url;
    }


}
