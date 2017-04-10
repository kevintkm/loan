package com.weimob.web.loan.Environment;

import com.weimob.web.loan.entities.BaseObject;

/**
 * Created by chris on 15/9/15.
 */
public class AppEnvironment extends BaseObject {

    private String appType = "android";     // android / ios
    private String appVersion = null;       // current version
    private String environment = null;      // dev / pl / release
    private String deviceId = null;         // android: imei; iOS: idfa
    private String channel = null;          // channel
    private String serviceUUID = null;

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        if (environment.equals(Environment.ENVIRONMENT_DEVELOPMENT)) {
            this.environment = "dev";
        } else if (environment.equals(Environment.ENVIRONMENT_PRE_RELEASE)) {
            this.environment = "pl";
        } else {
            this.environment = "release";
        }
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getServiceUUID() {
        return serviceUUID;
    }

    public void setServiceUUID(String serviceUUID) {
        this.serviceUUID = serviceUUID;
    }
}
