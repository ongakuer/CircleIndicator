package me.relex.circleindicatorsample;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import me.relex.circleindicator.CircleIndicator;

public class ViewpagerActivity extends ActionBarActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_activity);

        // DEFAULT
        ViewPager defaultViewpager = (ViewPager) findViewById(R.id.viewpager_default);
        CircleIndicator defaultIndicator = (CircleIndicator) findViewById(R.id.indicator_default);
        DemoPagerAdapter defaultPagerAdapter = new DemoPagerAdapter(getSupportFragmentManager());
        defaultViewpager.setAdapter(defaultPagerAdapter);
        defaultIndicator.setViewPager(defaultViewpager);

        // UNSELECTED BACKGROUND
        ViewPager unselectedBackgroundViewPager = (ViewPager) findViewById(R.id.viewpager_unselected_background);
        CircleIndicator unselectedBackgroundIndicator = (CircleIndicator) findViewById(R.id.indicator_unselected_background);
        DemoPagerAdapter unselectedBackgroundPagerAdapter = new DemoPagerAdapter(getSupportFragmentManager());
        unselectedBackgroundViewPager.setAdapter(unselectedBackgroundPagerAdapter);
        unselectedBackgroundIndicator.setViewPager(unselectedBackgroundViewPager);

        // CUSTOM
        ViewPager customViewpager = (ViewPager) findViewById(R.id.viewpager_custom);
        CircleIndicator customIndicator = (CircleIndicator) findViewById(R.id.indicator_custom);
        DemoPagerAdapter customPagerAdapter = new DemoPagerAdapter(getSupportFragmentManager());
        customViewpager.setAdapter(customPagerAdapter);
        customIndicator.setViewPager(customViewpager);
        customIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int i, float v, int i2) {

            }

            @Override public void onPageSelected(int i) {
                Log.d("OnPageChangeListener", "Current selected = " + i);
            }

            @Override public void onPageScrollStateChanged(int i) {

            }
        });
    }
}