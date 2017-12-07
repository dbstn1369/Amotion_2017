package com.amotion.amotion_2017.asynctask;

import android.os.AsyncTask;

import com.amotion.amotion_2017.data.AsyncData;
import com.amotion.amotion_2017.data.SubMenu;
import com.amotion.amotion_2017.data.Subject;
import com.amotion.amotion_2017.data.TableData;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSH on 2017-12-07.
 */

public class TableAsyncTask extends AsyncTask<AsyncData, String, ArrayList<Subject>>
{
    @Override
    protected ArrayList<Subject> doInBackground(AsyncData... asyncData)
    {
        ArrayList<Subject> subjects = asyncData[0].getSubjects();
        Map<String, String> loginTryCookie = asyncData[0].getLoginTryCookie();

        try
        {
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";

            for (int index = 0; index < subjects.size(); index++)
            {
                Subject subject = subjects.get(index);
                ArrayList<TableData> tableDataArrayList = new ArrayList<>();

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

                for (int subIndex = 0 ;subIndex<subject.getSubMenus().size();subIndex++ )
                {
                    Map<String, String> tableData = new HashMap<String, String>();
                    tableData.put("mnid", subject.getSubMenus().get(subIndex).getMnid());
                    tableData.put("board_no", subject.getSubMenus().get(subIndex).getBoard_no());

                    Connection.Response TableResponse = Jsoup.connect("http://e-learn.cnu.ac.kr/lms/class/boardItem/doListView.dunet")
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

                    Document tableDocument = TableResponse.parse();

                    //Log.d("SubjectSubmenuAsyncTask", subMenuDocument.toString());

                    String subjectTitle = tableDocument.select("p.list_tit").get(0).ownText() ;

                    Elements tableElements = tableDocument.select("table.list");
                    Elements tbodyElements = tableElements.select("tbody");
                    Elements tablerowElements = tbodyElements.select("tr");

                    if (tablerowElements.get(0).childNodeSize()==1){
                        continue;
                    }

                    for (int tablerowindex=0 ;tablerowindex<tablerowElements.size();tablerowindex++)
                    {
                        Element tr = tablerowElements.get(tablerowindex);
                        String id = tr.select("a[name=btn_board_view]").get(0).attr("id");
                        String title = tr.select("a[name=btn_board_view]").first().ownText();

                        tableDataArrayList.add(new TableData(title,
                                subject.getSubMenus().get(subIndex).getMenuName(),
                                id,
                                subjectTitle
                        ));
                    }
                }
                subjects.get(index).setTableDataArrayList(tableDataArrayList);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return subjects;
    }
}
