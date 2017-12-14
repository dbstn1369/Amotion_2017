package com.amotion.amotion_2017.data;

import java.util.Map;

/**
 * Created by JSH on 2017-12-09.
 */

public class CseBoardItemAsyncData {
    private Map<String, String> loginCookie;
    private CseBoardItem cseBoardItem;

    public CseBoardItemAsyncData(Map<String, String> loginCookie, CseBoardItem cseBoardItem) {
        this.loginCookie = loginCookie;
        this.cseBoardItem = cseBoardItem;
    }

    public Map<String, String> getLoginCookie() {
        return loginCookie;
    }

    public void setLoginCookie(Map<String, String> loginCookie) {
        this.loginCookie = loginCookie;
    }

    public CseBoardItem getCseBoardItem() {
        return cseBoardItem;
    }

    public void setCseBoardItem(CseBoardItem cseBoardItem) {
        this.cseBoardItem = cseBoardItem;
    }
}
