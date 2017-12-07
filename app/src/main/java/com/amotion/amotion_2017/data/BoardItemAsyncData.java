package com.amotion.amotion_2017.data;

import java.util.Map;

/**
 * Created by JSH on 2017-12-07.
 */

public class BoardItemAsyncData
{
    private Map<String, String> loginCookie;
    private TableData tableData;

    public BoardItemAsyncData(Map<String, String> loginCookie, TableData tableData)
    {
        this.loginCookie = loginCookie;
        this.tableData = tableData;
    }

    public Map<String, String> getLoginCookie()
    {
        return loginCookie;
    }

    public void setLoginCookie(Map<String, String> loginCookie)
    {
        this.loginCookie = loginCookie;
    }

    public TableData getTableData()
    {
        return tableData;
    }

    public void setTableData(TableData tableData)
    {
        this.tableData = tableData;
    }

    @Override
    public String toString()
    {
        return "BoardItemAsyncData{" +
                "loginCookie=" + loginCookie +
                ", tableData=" + tableData +
                '}';
    }
}
