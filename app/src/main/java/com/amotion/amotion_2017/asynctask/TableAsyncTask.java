package com.amotion.amotion_2017.asynctask;

import android.os.AsyncTask;

import com.amotion.amotion_2017.data.AsyncData;
import com.amotion.amotion_2017.data.SubMenu;
import com.amotion.amotion_2017.data.Subject;
import com.amotion.amotion_2017.data.TableAsyncData;
import com.amotion.amotion_2017.data.TableData;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSH on 2017-12-07.
 */

public class TableAsyncTask extends AsyncTask<TableAsyncData, String, ArrayList<TableData>> {
    @Override
    protected ArrayList<TableData> doInBackground(TableAsyncData... tableAsyncData) {
        Subject subject = tableAsyncData[0].getSubject();
        Map<String, String> loginTryCookie = tableAsyncData[0].getLoginTryCookie();

        try {
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";

            ArrayList<TableData> tableDataArrayList = new ArrayList<>();

            subjectConnection(subject, loginTryCookie, userAgent);

            for (int subIndex = 0; subIndex < subject.getSubMenus().size(); subIndex++) {
                Map<String, String> tableData = new HashMap<String, String>();
                tableData.put("mnid", subject.getSubMenus().get(subIndex).getMnid());
                tableData.put("board_no", subject.getSubMenus().get(subIndex).getBoard_no());

                Connection.Response tableResponse = Jsoup.connect("http://e-learn.cnu.ac.kr/lms/class/boardItem/doListView.dunet")

                        .userAgent(userAgent)
                        .timeout(60000)
                        .header("Origin", "http://e-learn.cnu.ac.kr")
                        .header("Referer", "http://e-learn.cnu.ac.kr/main/MainView.dunet")
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .data(tableData)
                        .cookies(loginTryCookie)
                        .method(Connection.Method.POST)
                        .ignoreContentType(true)
                        .execute();


                Document document = tableResponse.parse();

                Map<String, String> tableNumData = new HashMap<String, String>();

                tableNumData.put("page", String.valueOf(1));
                tableNumData.put("rows", String.valueOf(10));
                tableNumData.put("sord", "asc");
                tableNumData.put("course_id", subject.getCourse_id());
                tableNumData.put("class_no", subject.getClass_no());
                tableNumData.put("menu_type", "class");
                tableNumData.put("mode", "");
                tableNumData.put("q_mnid", subject.getSubMenus().get(subIndex).getMnid());
                tableNumData.put("boarditem_no", "");
                tableNumData.put("board_no", subject.getSubMenus().get(subIndex).getBoard_no());
                tableNumData.put("boarditem_depth", "");
                tableNumData.put("mode_ext", "");
                tableNumData.put("use_file_size", "0");
                tableNumData.put("category_id", "");
                tableNumData.put("q_where_type", "BOARDITEM_TITLE");
                tableNumData.put("q_key", "");

                int boardNum = document.select("div.pagination").get(0).childNodeSize();
                if (boardNum == 1) {
                    boardNum = 1;
                    Document tableDocument = tableResponse.parse();

                    //System.out.println(tableDocument.toString());

                    String subjectTitle = tableDocument.select("p.list_tit").get(0).ownText();

                    Elements tableElements = tableDocument.select("table.list");
                    Elements tbodyElements = tableElements.select("tbody");
                    Elements tablerowElements = tbodyElements.select("tr");

                    if (tablerowElements.get(0).childNodeSize() == 1) {
                        continue;
                    }

                    for (int tablerowindex = 0; tablerowindex < tablerowElements.size(); tablerowindex++) {
                        Element tr = tablerowElements.get(tablerowindex);
                        String id = tr.select("a[name=btn_board_view]").get(0).attr("id");
                        String title = tr.select("a[name=btn_board_view]").first().ownText();
                        tableNumData.put("boarditem_no", id);
                        String date = tr.child(4).ownText();
                        tableDataArrayList.add(new TableData(title,
                                subject.getSubMenus().get(subIndex).getMenuName(),
                                id,
                                subjectTitle,
                                tableNumData,
                                date
                        ));
                    }
                } else {
                    for (int i = 1; i < boardNum; i++) {

                        tableNumData.put("page", String.valueOf(i));

                        Connection.Response tableNoResponse = Jsoup.connect("http://e-learn.cnu.ac.kr/lms/class/boardItem/doListView.dunet")
                                .userAgent(userAgent)
                                .timeout(60000)
                                .header("Origin", "http://e-learn.cnu.ac.kr")
                                .header("Referer", "http://e-learn.cnu.ac.kr/main/MainView.dunet")
                                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                                .header("Content-Type", "application/x-www-form-urlencoded")
                                .header("Accept-Encoding", "gzip, deflate")
                                .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                                .data(tableNumData)
                                .cookies(loginTryCookie)
                                .method(Connection.Method.POST)
                                .ignoreContentType(true)
                                .execute();

                        Document tableDocument = tableNoResponse.parse();

                        //System.out.println(tableDocument.toString());

                        String subjectTitle = tableDocument.select("p.list_tit").get(0).ownText();

                        Elements tableElements = tableDocument.select("table.list");
                        Elements tbodyElements = tableElements.select("tbody");
                        Elements tablerowElements = tbodyElements.select("tr");

                        if (tablerowElements.get(0).childNodeSize() == 1) {
                            continue;
                        }

                        for (int tablerowindex = 0; tablerowindex < tablerowElements.size(); tablerowindex++) {
                            Element tr = tablerowElements.get(tablerowindex);
                            String id = tr.select("a[name=btn_board_view]").get(0).attr("id");
                            String title = tr.select("a[name=btn_board_view]").first().ownText();

                            Map<String, String> temp = new HashMap<String, String>(tableNumData);
                            temp.put("boarditem_no", id);
                            String date = tr.child(4).ownText();
                            tableDataArrayList.add(new TableData(title,
                                    subject.getSubMenus().get(subIndex).getMenuName(),
                                    id,
                                    subjectTitle,
                                    temp,
                                    date
                            ));
                        }
                    }


                    //Log.d("SubjectSubmenuAsyncTask", subMenuDocument.toString());

                }

                subject.setTableDataArrayList(tableDataArrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void subjectConnection(Subject subject, Map<String, String> loginTryCookie, String userAgent) throws IOException {
        Map<String, String> subMenuData = new HashMap<String, String>();
        subMenuData.put("mnid", subject.getMnid());
        subMenuData.put("course_id", subject.getCourse_id());
        subMenuData.put("class_no", subject.getClass_no());
        subMenuData.put("term_year", subject.getTerm_year());
        subMenuData.put("term_cd", subject.getTerm_cd());
        subMenuData.put("subject_cd", subject.getSubject_cd());
        subMenuData.put("user_no", subject.getUser_no());

        Connection.Response subjectResponse = Jsoup.connect("http://e-learn.cnu.ac.kr/lms/class/classroom/doViewClassRoom_new.dunet")
                .userAgent(userAgent)
                .timeout(60000)
                .header("Origin", "http://e-learn.cnu.ac.kr")
                .header("Referer", "http://e-learn.cnu.ac.kr/main/MainView.dunet")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .data(subMenuData)
                .cookies(loginTryCookie)
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .execute();
    }
}
