package com.amotion.amotion_2017.data;

import java.util.ArrayList;

/**
 * Created by JSH on 2017-11-26.
 */
public class Subject {
    private String mnid = "201008254671";
    private String course_id;
    private String class_no;
    private String term_year;
    private String term_cd;
    private String subject_cd;
    private String user_no;
    private ArrayList<SubMenu> subMenus;
    private ArrayList<TableData> tableDataArrayList;
    private String subjectName;

    public ArrayList<SubMenu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(ArrayList<SubMenu> subMenus) {
        this.subMenus = subMenus;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getMnid() {
        return mnid;
    }

    public void setMnid(String mnid) {
        this.mnid = mnid;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getClass_no() {
        return class_no;
    }

    public void setClass_no(String class_no) {
        this.class_no = class_no;
    }

    public String getTerm_year() {
        return term_year;
    }

    public void setTerm_year(String term_year) {
        this.term_year = term_year;
    }

    public String getTerm_cd() {
        return term_cd;
    }

    public void setTerm_cd(String term_cd) {
        this.term_cd = term_cd;
    }

    public String getSubject_cd() {
        return subject_cd;
    }

    public void setSubject_cd(String subject_cd) {
        this.subject_cd = subject_cd;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }
    public ArrayList<TableData> getTableDataArrayList()
    {
        return tableDataArrayList;
    }

    public void setTableDataArrayList(ArrayList<TableData> tableDataArrayList)
    {
        this.tableDataArrayList = tableDataArrayList;
    }

    @Override
    public String toString() {
        return "Subject{ \n" +
                " mnid=' " + mnid + " \', \n" +
                " course_id=' " + course_id + " \', \n" +
                " class_no=' " + class_no + " \', \n" +
                " term_year=' " + term_year + " \', \n" +
                " term_cd=' " + term_cd + " \', \n" +
                " subject_cd=' " + subject_cd + " \', \n" +
                " user_no=' " + user_no + " \', \n" +
                " subjectName=' " + subjectName + " \'" +
                '}';
    }
}
