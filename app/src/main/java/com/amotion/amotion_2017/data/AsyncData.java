package com.amotion.amotion_2017.data;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by JSH on 2017-12-04.
 */

public class AsyncData {
    private ArrayList<Subject> subjects;
    private Map<String, String> loginTryCookie;

    public AsyncData(Map<String, String> loginTryCookie, ArrayList<Subject> subjects) {
        this.subjects = subjects;
        this.loginTryCookie = loginTryCookie;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public Map<String, String> getLoginTryCookie() {
        return loginTryCookie;
    }

    public void setLoginTryCookie(Map<String, String> loginTryCookie) {
        this.loginTryCookie = loginTryCookie;
    }
}
