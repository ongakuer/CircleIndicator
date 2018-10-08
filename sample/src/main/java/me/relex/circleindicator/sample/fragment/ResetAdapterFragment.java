package me.relex.circleindicator.sample.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Random;
import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.sample.R;
import me.relex.circleindicator.sample.SamplePagerAdapter;

public class ResetAdapterFragment extends Fragment {

    private final Random mRandom = new Random();

    private ViewPager mViewpager;
    private CircleIndicator mIndicator;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample_reset_adapter, container, false);
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewpager = view.findViewById(R.id.viewpager);
        mIndicator = view.findViewById(R.id.indicator);
        mViewpager.setAdapter(new SamplePagerAdapter(5));
        mIndicator.setViewPager(mViewpager);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mViewpager.setAdapter(new SamplePagerAdapter(1 + mRandom.nextInt(5)));
                mIndicator.setViewPager(mViewpager);
            }
        });
    }
}
