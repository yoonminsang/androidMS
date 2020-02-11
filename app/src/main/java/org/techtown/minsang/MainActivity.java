package org.techtown.minsang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private BackPressedForFinish backPressedForFinish;
    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    private FragmentAdapter_main mFragmentAdapter_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        backPressedForFinish = new BackPressedForFinish(this);
        mTabLayout = (TabLayout) findViewById(R.id.layout_tab);
        mTabLayout.addTab(mTabLayout.newTab().setText("달력").setIcon(R.drawable.calendar));
        mTabLayout.addTab(mTabLayout.newTab().setText("메모장").setIcon(R.drawable.diary));
        mTabLayout.addTab(mTabLayout.newTab().setText("운동루틴").setIcon(R.drawable.exerciseroutine));
        mTabLayout.addTab(mTabLayout.newTab().setText("미니게임").setIcon(R.drawable.minigame));
        mTabLayout.addTab(mTabLayout.newTab().setText("프로필").setIcon(R.drawable.profile));
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
//        mViewpager.setPagingEnabled(false);
        mFragmentAdapter_main = new FragmentAdapter_main(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewpager.setAdapter(mFragmentAdapter_main);
        mViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        backPressedForFinish.onBackPressed();
    }

}
