package com.example.winterwork;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
private ImageView background1;
private String res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        LoadPhoto();


    }
    private void initview(){
        background1=findViewById(R.id.login_img);
    }
//    下载图片
    private void LoadPhoto(){
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
                Toast.makeText(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               res=response.body().string();
                Log.d("TAG", "onResponse: "+res);
                try {
                    JSONObject jsonObject=new JSONObject(res);
                    JSONObject data1=jsonObject.getJSONObject("data");
                    String address=data1.getString("url");
                    Glide.with(MainActivity.this).load(address).into(background1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}