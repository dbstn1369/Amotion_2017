package com.amotion.amotion_2017;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.amotion.amotion_2017.asynctask.BoardItemAsyncTask;
import com.amotion.amotion_2017.View.ActivityLogin;
import com.amotion.amotion_2017.asynctask.CseBoardAsyncTask;
import com.amotion.amotion_2017.asynctask.CseLoginAsyncTask;
import com.amotion.amotion_2017.asynctask.LoginAsyncTask;
import com.amotion.amotion_2017.asynctask.ScheduleAsyncTask;
import com.amotion.amotion_2017.asynctask.SubjectAsyncTask;
import com.amotion.amotion_2017.asynctask.SubjectSubmenuAsyncTask;
import com.amotion.amotion_2017.asynctask.TableAsyncTask;
import com.amotion.amotion_2017.data.AsyncData;
import com.amotion.amotion_2017.data.BoardItemAsyncData;
import com.amotion.amotion_2017.data.CseAsyncData;
import com.amotion.amotion_2017.data.CseBoardItem;
import com.amotion.amotion_2017.data.Schedule;
import com.amotion.amotion_2017.fragment.FragmentCnu;
import com.amotion.amotion_2017.fragment.FragmentHome;
import com.amotion.amotion_2017.fragment.FragmentSubject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public static Map<String, String> loginCookie = null;
    public static Map<String, String> cseLoginCookie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cnup_main);
        //이러닝 로그인 정보를 사용하여 로그인 쿠키를 얻어옴
        //컴공사이트 로그인 정보를 사용하여 로그인 쿠키를 얻어옴

        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "putid");
        map.put("pw", "putpw");

        Map<String, String> cseMap = new HashMap<String, String>();
        cseMap.put("id", "putid");
        cseMap.put("pw", "putpw");

        try {
            loginCookie = new LoginAsyncTask(getApplicationContext()).execute(map).get();
            cseLoginCookie = new CseLoginAsyncTask(getApplicationContext()).execute(cseMap).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }




        //slide관련
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        //slide끝
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
   }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //옵션 메뉴 관련
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //옵션 설정
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //슬라이드 관련 메뉴이름 설정 및 내부 fragment관리
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        FragmentHome fragmentHome=new FragmentHome();
        FragmentCnu fragmentCnu = new FragmentCnu();
        FragmentSubject fragmentSubject = new FragmentSubject();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //슬라이드 화면 구성
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return fragmentHome;
                case 1:
                    return fragmentSubject;
                case 2:
                    return fragmentCnu;
            }
            return null;
        }

        //최대 탭 메뉴 설정
        @Override
        public int getCount() {
            return 3;
        }

        //탭버튼 제목관련
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "HOME";
                case 1:
                    return "E-LEARNING";
                case 2:
                    return "COMPUTER";
            }
            return null;
        }
    }

    //추후 클래스로 분리할것



}
