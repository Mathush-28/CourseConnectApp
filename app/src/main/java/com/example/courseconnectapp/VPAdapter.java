package com.example.courseconnectapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class VPAdapter extends FragmentStateAdapter {
    public VPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0: return new fragment1();
            case 1: return new fragment2();
            case 2: return new fragment3();
            default: return new fragment1();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
