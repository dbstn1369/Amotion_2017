package com.amotion.amotion_2017.data;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by JSH on 2017-12-04.
 */

public class TableAsyncData {
    private Subject subject;
    private Map<String, String> loginTryCookie;

    public TableAsyncData(Subject subject, Map<String, String> loginTryCookie) {
        this.subject = subject;
        this.loginTryCookie = loginTryCookie;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Map<String, String> getLoginTryCookie() {
        return loginTryCookie;
    }

    public void setLoginTryCookie(Map<String, String> loginTryCookie) {
        this.loginTryCookie = loginTryCookie;
    }
}
