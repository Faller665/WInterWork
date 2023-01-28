package com.example.winterwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main extends AppCompatActivity {
    private String weatherJson;
    public String weather;
private ViewPager2 vp;
private ArrayList<BackInterface> data;
private Fragment fragment_today;
private Fragment fragment_future;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getData();
        data.add(new BackInterface() {
            @Override
            public Fragment back() {
                return  fragment_today;
            }
        });
        data.add(new BackInterface() {
            @Override
            public Fragment back() {
                return fragment_future;
            }
        });
        VP_adapter adapter=new VP_adapter(this,data);
        vp.setAdapter(adapter);
    }
    private void initView(){
         vp=findViewById(R.id.Vp2_mian);
         data=new ArrayList<>();
fragment_today=new Fragment_today();
fragment_future=new Fragment_future();
    }
//    通过网络请求获取数据
private void getData(){
    OkHttpClient client=new OkHttpClient();
    HttpUrl.Builder builder=HttpUrl.parse("https://v2.alapi.cn/api/weather/forecast").newBuilder();
    builder.addQueryParameter("location","重庆");
    Request request=new Request.Builder()
            .url(builder.build())
            .addHeader("token","RAOQHDgr4TZmvLkl")
            .build();
    Call call= client.newCall(request);
    call.enqueue(new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            Toast.makeText(Main.this, "请求失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            weatherJson=response.body().string();
            try {
                JSONObject jsonObject=new JSONObject(weatherJson);
                JSONObject data=jsonObject.getJSONObject("data");
                JSONArray daily_forecast=data.getJSONArray("daily_forecast");
                for (int i = 0; i < 1; i++) {
                    JSONObject today=daily_forecast.getJSONObject(i);
                    weather=today.getString("cond_txt_n");
                }
                  send(fragment_today,weather);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
}
private void send(Fragment fragment, String item){
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    Bundle bundle=new Bundle();
    bundle.putString("data",item);
    fragment.setArguments(bundle);
    fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.fragment_container,fragment);
    fragmentTransaction.commit();
}

}