package me.relex.circleindicator.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Random;
import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.sample.SamplePagerAdapter;
import me.relex.circleindicator.sample.R;

public class ResetAdapterFragment extends Fragment implements View.OnClickListener {

    private final Random mRandom = new Random();

    private ViewPager mViewpager;
    private CircleIndicator mIndicator;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample_reset_adapter, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.reset).setOnClickListener(this);
        mViewpager = (ViewPager) view.findViewById(R.id.viewpager);
        mIndicator = (CircleIndicator) view.findViewById(R.id.indicator);
        mViewpager.setAdapter(new SamplePagerAdapter(5));
        mIndicator.setViewPager(mViewpager);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset:
                mViewpager.setAdapter(new SamplePagerAdapter(1 + mRandom.nextInt(5)));
                mIndicator.setViewPager(mViewpager);
                break;
        }
    }
}
