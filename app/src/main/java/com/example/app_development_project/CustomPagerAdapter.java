package com.example.app_development_project;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import Fragments.CarInventoryFragment;

public class CustomPagerAdapter extends FragmentStateAdapter {

    private final int numTabs;

    public CustomPagerAdapter(@NonNull FragmentActivity fragmentActivity, int numTabs) {
        super(fragmentActivity);
        this.numTabs = numTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new CarInventoryFragment();
    }

    @Override
    public int getItemCount() {
        return numTabs;
    }
}

