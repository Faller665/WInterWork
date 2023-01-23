package com.example.winterwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Register extends AppCompatActivity {
private EditText username;
private EditText password;
private EditText rePassword;
private Button register;
private String username2;
private String password2;
private String rePassword2;
private String registerJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username2=username.getText().toString();
                password2=password.getText().toString();
                rePassword2=rePassword.getText().toString();
                register();
            }
        });
    }
    private void initView(){
        username=findViewById(R.id.et_name1);
        password=findViewById(R.id.et_password1);
        register=findViewById(R.id.Bt_register1);
        rePassword=findViewById(R.id.editText);
    }
    private void register(){
        if (!username2.equals("")&&!password2.equals("")&&!rePassword2.equals("")){
            OkHttpClient client=new OkHttpClient();
            FormBody formBody=new FormBody.Builder()
                    .add("username",username2)
                    .add("password",password2)
                    .add("repassword",rePassword2)
                    .build();
            Request request=new Request.Builder()
                    .url("https://www.wanandroid.com/user/register")
                    .post(formBody)
                    .build();
            Call call= client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Toast.makeText(Register.this, "注册失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                   registerJson=response.body().string();
                    try {
                        JSONObject jsonObject=new JSONObject(registerJson);
                        int errorCode=jsonObject.getInt("errorCode");
                        if(errorCode==0){
                            Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Register.this, "该账号已被注册", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else{
            Toast.makeText(this, "账号或者密码为空", Toast.LENGTH_SHORT).show();
        }
    }
}