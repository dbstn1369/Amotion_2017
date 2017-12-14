package com.amotion.amotion_2017.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amotion.amotion_2017.MainActivity;
import com.amotion.amotion_2017.R;
import com.amotion.amotion_2017.asynctask.CseLoginAsyncTask;
import com.amotion.amotion_2017.asynctask.LoginAsyncTask;

import java.util.HashMap;
import java.util.Map;

public class ActivityLogin extends AppCompatActivity
{
    EditText cseID_Input;
    EditText csePW_Input;
    EditText eLearnID_Input;
    EditText eLearnPW_Input;
    SharedPreferences test;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent temp = getIntent();

        test = getSharedPreferences("login", MODE_PRIVATE);
        if (
                test.contains("cseID") &&
                        (!getIntent().hasExtra("requestcode"))){
            Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        cseID_Input = (EditText) findViewById(R.id.cse_ID_Input);
        csePW_Input = (EditText) findViewById(R.id.csePassword);
        eLearnID_Input = (EditText) findViewById(R.id.eLearn_ID_Input);
        eLearnPW_Input = (EditText) findViewById(R.id.eLearnPassword);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        cseID_Input.setText(test.getString("cseID", ""));
        csePW_Input.setText(test.getString("csePW", ""));
        eLearnID_Input.setText(test.getString("eLearnID", ""));
        eLearnPW_Input.setText(test.getString("eLearnPW", ""));

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String cseID = cseID_Input.getText().toString();
                String csePW = csePW_Input.getText().toString();
                String eLearnID = eLearnID_Input.getText().toString();
                String eLearnPW = eLearnPW_Input.getText().toString();

                cseID = cseID.trim();
                csePW = csePW.trim();
                eLearnID = eLearnID.trim();
                eLearnPW = eLearnPW.trim();

                if (cseID.isEmpty())
                {
                    Toast.makeText(ActivityLogin.this, "CSE ID를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if (csePW.isEmpty())
                {
                    Toast.makeText(ActivityLogin.this, "CSE PW를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if (eLearnID.isEmpty())
                {
                    Toast.makeText(ActivityLogin.this, "이러닝 ID를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if (eLearnPW.isEmpty())
                {
                    Toast.makeText(ActivityLogin.this, "이러닝 pw를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }

                try
                {
                    Map<String, String> eLearnLogin = new HashMap<>();
                    eLearnLogin.put("id", cseID);
                    eLearnLogin.put("pw", csePW);

                    Map<String, String> eLearnLoginCookie = new LoginAsyncTask(ActivityLogin.this).execute(eLearnLogin).get();

                    Map<String, String> cseLogin = new HashMap<>();
                    cseLogin.put("id", cseID);
                    cseLogin.put("pw", csePW);

                    Map<String, String> cseLoginCookie = new CseLoginAsyncTask().execute(cseLogin).get();

                    if (eLearnLoginCookie.size()!=2){
                        Toast.makeText(ActivityLogin.this, "이러닝 ID와 PW를 확인해주세요", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (cseLoginCookie.size()!=4){
                        Toast.makeText(ActivityLogin.this, "컴퓨터공학과 ID와 PW를 확인해주세요", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                //SharedPreferences test = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = test.edit();
                editor.putString("cseID", cseID);
                editor.putString("csePW", csePW);
                editor.putString("eLearnID", eLearnID);
                editor.putString("eLearnPW", eLearnPW);

                editor.commit();

                Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                setResult(RESULT_OK);
                startActivity(intent);
                finish();

            }
        });

    }
}
