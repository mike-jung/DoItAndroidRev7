package org.techtown.pager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 pager = findViewById(R.id.pager);
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setOffscreenPageLimit(3);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), getLifecycle());
        pager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("첫번째");
                        break;
                    case 1:
                        tab.setText("두번째");
                        break;
                    case 2:
                        tab.setText("세번째");
                        break;
                }
            }
        }).attach();

    }


    class MyPagerAdapter extends FragmentStateAdapter {
        int itemCount = 3;

        public MyPagerAdapter(FragmentManager fm, Lifecycle cycle) {
            super(fm, cycle);
        }

        @Override
        public int getItemCount() {
            return itemCount;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position) {
                case 0: {
                    return new Fragment1();
                }
                case 1: {
                    return new Fragment2();
                }
                case 2: {
                    return new Fragment3();
                }
            }
            return new Fragment1();
        }

    }

}