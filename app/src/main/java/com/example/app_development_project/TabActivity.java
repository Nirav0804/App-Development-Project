package com.example.app_development_project;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_development_project.R;
import com.example.carshowroom.TabPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;

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
        adapter.addFragment(new TestDriveFragment(), "Test Drive");

        // Set adapter to ViewPager
        viewPager.setAdapter(adapter);

        // Attach TabLayout with ViewPager2 using TabLayoutMediator
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(adapter.getPageTitle(position));
            }
        }).attach();
    }
}
