package me.relex.circleindicator.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.OnIndicatorClickListener;
import me.relex.circleindicator.sample.DemoPagerAdapter;
import me.relex.circleindicator.sample.R;

public class OnIndicatorClickFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_on_indicator_click_demo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final ViewPager viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        viewpager.setAdapter(new DemoPagerAdapter());
        indicator.setOnIndicatorClickListener(new OnIndicatorClickListener() {
            @Override
            public void onIndicatorClick(int position) {
                viewpager.setCurrentItem(position, true);
            }
        });

        indicator.setViewPager(viewpager);

        viewpager.setCurrentItem(2);

    }
}
