package com.amotion.amotion_2017.data;

import java.util.Map;

/**
 * Created by JSH on 2017-12-09.
 */

public class CseAsyncData {

    private Map<String, String> loginCookies;
    private String Url;

    public CseAsyncData(Map<String, String> loginCookies, String url) {
        this.loginCookies = loginCookies;
        this.Url = url;
    }

    public Map<String, String> getLoginCookies() {
        return loginCookies;
    }

    public void setLoginCookies(Map<String, String> loginCookies) {
        this.loginCookies = loginCookies;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
