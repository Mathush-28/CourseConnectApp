package com.example.courseconnectapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class VPAdapter2 extends FragmentStateAdapter {
    public VPAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0: return new Gfragment1();
            case 1: return new GFragment2();
            default: return new Gfragment1();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
