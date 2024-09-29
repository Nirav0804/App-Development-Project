package com.example.app_development_project;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_development_project.R;
import com.example.carshowroom.TabPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class TabActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TabPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Initialize TabPagerAdapter
        adapter = new TabPagerAdapter(this);

        // Add fragments to the adapter
        adapter.addFragment(new BookCarFragment(), "Book a Car");
        adapter.addFragment(new CarServiceFragment(), "Car Service");
        adapter.addFragment(new CarInventoryFragment(), "Car Inventory");

        // Set adapter to ViewPager
        viewPager.setAdapter(adapter);

        // Set tab titles manually
        tabLayout.addTab(tabLayout.newTab().setText("Book a Car"));
        tabLayout.addTab(tabLayout.newTab().setText("Car Service"));
        tabLayout.addTab(tabLayout.newTab().setText("Car Inventory"));

        // Synchronize ViewPager and TabLayout manually
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // When a tab is selected, switch to the corresponding fragment
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Not used
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Not used
            }
        });

        // Handle page swiping and change the tab accordingly
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Objects.requireNonNull(tabLayout.getTabAt(position)).select();
            }
        });
    }
}
