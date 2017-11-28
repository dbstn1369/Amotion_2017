package com.amotion.amotion_2017;

/**
 * Created by JSH on 2017-11-28.
 */

public class SubMenu {
    private String mnid;
    private String board_no;
    private String menuName;

    public String getMnid() {
        return mnid;
    }

    public void setMnid(String mnid) {
        this.mnid = mnid;
    }

    public String getBoard_no() {
        return board_no;
    }

    public void setBoard_no(String board_no) {
        this.board_no = board_no;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Override
    public String toString() {
        return "SubMenu{" +
                "mnid=' " + mnid + " \'"+
                ", board_no=' " + board_no + " \'" +
                ", menuName=' " + menuName + " \'" +
                '}';
    }
}
