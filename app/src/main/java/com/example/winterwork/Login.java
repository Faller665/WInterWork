package com.example.winterwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends AppCompatActivity {
private ImageView background1;
private EditText name;
private EditText password;
private ImageView name1;
private ImageView password1;
private String PhotoJson;
private String LoginJson;
private String UserName;
private String Password;
private Button login;
private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        loadPhoto();
        front();
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               UserName=name.getText().toString();
               Password=password.getText().toString();
               login();
           }
       });
       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(Login.this,Register.class);
               startActivity(intent);
           }
       });

    }
//    控件的初始化
    private void initView(){
        background1=findViewById(R.id.login_img);
        name=findViewById(R.id.et_name);
        password=findViewById(R.id.et_password);
        name1=findViewById(R.id.imageView);
        password1=findViewById(R.id.imageView2);
        login=findViewById(R.id.Bt_login);
        register=findViewById(R.id.Bt_register);
    }
//    将控件置于最上层
    private void front(){
        name1.bringToFront();
        password1.bringToFront();
        name.bringToFront();
        password.bringToFront();
    }
//    下载图片
    public void loadPhoto(){try {
        OkHttpClient client = new OkHttpClient();
        Request.Builder RequestBuilder=new Request.Builder();
        RequestBuilder.addHeader("token","RAOQHDgr4TZmvLkl");
        HttpUrl.Builder UrlBuilder=HttpUrl.parse("https://v2.alapi.cn/api/bing").newBuilder();
        UrlBuilder.addQueryParameter("format","json");
        RequestBuilder.url(UrlBuilder.build());
        Request request=RequestBuilder.build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(Login.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PhotoJson=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(PhotoJson);
                    JSONObject data1=jsonObject.getJSONObject("data");
                    String address=data1.getString("url");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(Login.this).load(address).into(background1);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    } catch (Exception e) {
        e.printStackTrace();
    }

    }

    private void login(){
        if (!UserName.equals("")&&!Password.equals("")){
            OkHttpClient client=new OkHttpClient();
            FormBody formBody=new FormBody.Builder()
                    .add("username",UserName)
                    .add("password",Password).build();
            Request request=new Request.Builder()
                    .url("https://www.wanandroid.com/user/login")
                    .post(formBody)
                    .build();
            Call call=client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Toast.makeText(Login.this, "没有注册", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    LoginJson=response.body().string();
                    try {
                        JSONObject jsonObject=new JSONObject(LoginJson);

                        int errorCode=jsonObject.getInt("errorCode");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(errorCode==0){
                                    Intent intent=new Intent(Login.this,Main.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(Login.this, "账号或者密码不匹配或者该账号未注册", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        else{
            Toast.makeText(Login.this, "登陆失败，账号或者密码为空", Toast.LENGTH_SHORT).show();
        }
    }
}