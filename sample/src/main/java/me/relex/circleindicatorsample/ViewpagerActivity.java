package me.relex.circleindicatorsample;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import me.relex.circleindicator.CircleIndicator;

public class ViewpagerActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Integer> defaultViewPagerColors;
    private ArrayList<Integer> customViewPagerColors;
    private ArrayList<Integer> unselectedViewPagerColors;

    private ColorViewPagerAdapter defaultViewPagerAdapter;
    private ColorViewPagerAdapter customViewPagerAdapter;
    private ColorViewPagerAdapter unselectedViewPagerAdapter;

    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_activity);

        random = new Random(System.currentTimeMillis());

        // DEFAULT
        findViewById(R.id.viewpager_default_add).setOnClickListener(this);
        findViewById(R.id.viewpager_default_remove).setOnClickListener(this);
        ViewPager defaultViewpager = (ViewPager) findViewById(R.id.viewpager_default);
        CircleIndicator defaultIndicator = (CircleIndicator) findViewById(R.id.indicator_default);
        defaultViewPagerColors = initializeColors();
        defaultViewPagerAdapter = new ColorViewPagerAdapter(defaultViewPagerColors);
        defaultViewpager.setAdapter(defaultViewPagerAdapter);
        defaultIndicator.setViewPager(defaultViewpager);

        // CUSTOM
        findViewById(R.id.viewpager_custom_add).setOnClickListener(this);
        findViewById(R.id.viewpager_custom_remove).setOnClickListener(this);
        ViewPager customViewpager = (ViewPager) findViewById(R.id.viewpager_custom);
        CircleIndicator customIndicator = (CircleIndicator) findViewById(R.id.indicator_custom);
        customViewPagerColors = initializeColors();
        customViewPagerAdapter = new ColorViewPagerAdapter(customViewPagerColors);
        customViewpager.setAdapter(customViewPagerAdapter);
        customIndicator.setViewPager(customViewpager);
        customViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.d("OnPageChangeListener", "Current selected = " + i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // UNSELECTED BACKGROUND
        findViewById(R.id.viewpager_unselected_background_add).setOnClickListener(this);
        findViewById(R.id.viewpager_unselected_background_remove).setOnClickListener(this);
        ViewPager unselectedBackgroundViewPager =
                (ViewPager) findViewById(R.id.viewpager_unselected_background);
        CircleIndicator unselectedBackgroundIndicator =
                (CircleIndicator) findViewById(R.id.indicator_unselected_background);
        unselectedViewPagerColors = initializeColors();
        unselectedViewPagerAdapter = new ColorViewPagerAdapter(unselectedViewPagerColors);
        unselectedBackgroundViewPager.setAdapter(unselectedViewPagerAdapter);
        unselectedBackgroundIndicator.setViewPager(unselectedBackgroundViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewpager_default_add:
                defaultViewPagerColors.add(newColor());
                defaultViewPagerAdapter.notifyDataSetChanged();
                break;
            case R.id.viewpager_default_remove:
                if (defaultViewPagerColors.size() > 0) {
                    defaultViewPagerColors.remove(defaultViewPagerColors.size() - 1);
                    defaultViewPagerAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.viewpager_custom_add:
                customViewPagerColors.add(newColor());
                customViewPagerAdapter.notifyDataSetChanged();
                break;
            case R.id.viewpager_custom_remove:
                if (customViewPagerColors.size() > 0) {
                    customViewPagerColors.remove(customViewPagerColors.size() - 1);
                    customViewPagerAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.viewpager_unselected_background_add:
                unselectedViewPagerColors.add(newColor());
                unselectedViewPagerAdapter.notifyDataSetChanged();
                break;
            case R.id.viewpager_unselected_background_remove:
                if (unselectedViewPagerColors.size() > 0) {
                    unselectedViewPagerColors.remove(unselectedViewPagerColors.size() - 1);
                    unselectedViewPagerAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private ArrayList<Integer> initializeColors() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(newColor());
        colors.add(newColor());
        colors.add(newColor());
        colors.add(newColor());
        colors.add(newColor());
        return colors;
    }

    private int newColor() {
        return 0xff000000 | random.nextInt(0x00ffffff);
    }
}