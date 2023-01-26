package com.example.winterwork;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class VP_adapter extends FragmentStateAdapter {
    public final ArrayList<BackInterface> data;

    public VP_adapter(@NonNull FragmentActivity fragmentActivity, ArrayList<BackInterface> data){
        super(fragmentActivity);
        this.data=data;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return data.get(position).back();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
