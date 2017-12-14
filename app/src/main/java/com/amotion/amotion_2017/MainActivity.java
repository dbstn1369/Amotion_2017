package com.amotion.amotion_2017;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.amotion.amotion_2017.service.PushService;
import com.amotion.amotion_2017.service.StartService;
import com.amotion.amotion_2017.asynctask.CseBoardAsyncTask;
import com.amotion.amotion_2017.asynctask.CseBoardItemAsyncTask;
import com.amotion.amotion_2017.asynctask.CseLoginAsyncTask;
import com.amotion.amotion_2017.asynctask.LoginAsyncTask;
import com.amotion.amotion_2017.data.Board;
import com.amotion.amotion_2017.data.CseAsyncData;
import com.amotion.amotion_2017.data.CseBoardItem;
import com.amotion.amotion_2017.data.CseBoardItemAsyncData;

import com.amotion.amotion_2017.fragment.FragmentCnu;
import com.amotion.amotion_2017.fragment.FragmentSchedule;
import com.amotion.amotion_2017.fragment.FragmentSubject;
import com.amotion.amotion_2017.view.ActivityLogin;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
{
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public static Map<String, String> loginCookie = null;
    public static Map<String, String> cseLoginCookie = null;
    BroadcastReceiver pushReciver;
    SharedPreferences test;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cnup_main);
        //이러닝 로그인 정보를 사용하여 로그인 쿠키를 얻어옴
        //컴공사이트 로그인 정보를 사용하여 로그인 쿠키를 얻어옴

        test = getSharedPreferences("login", MODE_PRIVATE);
       // test.getString("Subjects", "");

        Map<String, String> map = new HashMap<String, String>();
        map.put("id", test.getString("eLearnID", ""));
        map.put("pw", test.getString("eLearnPW", ""));

        Map<String, String> cseMap = new HashMap<String, String>();
        cseMap.put("id", test.getString("cseID", ""));
        cseMap.put("pw", test.getString("csePW", ""));

        try
        {
            loginCookie = new LoginAsyncTask().execute(map).get();
            cseLoginCookie = new CseLoginAsyncTask().execute(cseMap).get();

            ArrayList<CseBoardItem> cseBoardItems = new CseBoardAsyncTask().execute(new CseAsyncData(loginCookie, "http://computer.cnu.ac.kr/index.php?mid=notice")).get();
            CseBoardItemAsyncData cseBoardItemAsyncData = new CseBoardItemAsyncData(cseLoginCookie, cseBoardItems.get(0));
            Board board = new CseBoardItemAsyncTask().execute(cseBoardItemAsyncData).get();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
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


        Intent intentMyService = new Intent(this, PushService.class);
        pushReciver = new StartService();
        try
        {
            IntentFilter mainFilter = new IntentFilter("com.amotion.amotion_2017");
            // 리시버 저장
            registerReceiver(pushReciver, mainFilter);
            // 서비스 시작
            startService(intentMyService);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 102)
        {
            if (resultCode == RESULT_OK)
            {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", test.getString("eLearnID", ""));
                map.put("pw", test.getString("eLearnPW", ""));

                Map<String, String> cseMap = new HashMap<String, String>();
                cseMap.put("id", test.getString("cseID", ""));
                cseMap.put("pw", test.getString("csePW", ""));

                try
                {
                    loginCookie = new LoginAsyncTask().execute(map).get();
                    cseLoginCookie = new CseLoginAsyncTask().execute(cseMap).get();

                    ArrayList<CseBoardItem> cseBoardItems = new CseBoardAsyncTask().execute(new CseAsyncData(loginCookie, "http://computer.cnu.ac.kr/index.php?mid=notice")).get();
                    CseBoardItemAsyncData cseBoardItemAsyncData = new CseBoardItemAsyncData(cseLoginCookie, cseBoardItems.get(0));
                    Board board = new CseBoardItemAsyncTask().execute(cseBoardItemAsyncData).get();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                } catch (ExecutionException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    protected void onResumeFragments()
    {
        super.onResumeFragments();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    //옵션 메뉴 관련
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //옵션 설정
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
            intent.putExtra("requestcode", 102);
            startActivityForResult(intent, 102);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //슬라이드 관련 메뉴이름 설정 및 내부 fragment관리
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        FragmentSchedule fragmentHome = new FragmentSchedule();
        FragmentCnu fragmentCnu = new FragmentCnu();
        FragmentSubject fragmentSubject = new FragmentSubject();

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        //슬라이드 화면 구성
        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
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
        public int getCount()
        {
            return 3;
        }

        //탭버튼 제목관련
        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
                case 0:
                    return "Schedule";
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
