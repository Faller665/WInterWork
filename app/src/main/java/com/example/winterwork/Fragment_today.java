package com.example.winterwork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fragment_today extends Fragment {

    public TextView Weather;
    public Bundle bundle;
    public String data;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View mView=inflater.inflate(R.layout.fragment_taday,container,false);
            Weather=mView.findViewById(R.id.tt_weather);
            bundle=this.getArguments();
            data=bundle.getString("data");
            Weather.setText(data);
        return mView;
    }
    public void onViewCreated(View view,Bundle savedInstanceState){

        super.onViewCreated(view,savedInstanceState);
    }

}
