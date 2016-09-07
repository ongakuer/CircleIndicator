package me.relex.circleindicator.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.sample.R;
import me.relex.circleindicator.sample.SamplePagerAdapter;

public class SnackbarBehaviorFragment extends Fragment {

    private Snackbar mSnackbar;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample_snackbar_behavior, container, false);
    }

    @Override public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        ViewPager viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        viewpager.setAdapter(new SamplePagerAdapter());
        indicator.setViewPager(viewpager);

        mSnackbar = Snackbar.make(view.findViewById(R.id.coordinator_layout), "Snackbar",
                Snackbar.LENGTH_SHORT);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (!mSnackbar.isShown()) {
                    mSnackbar.show();
                } else {
                    mSnackbar.dismiss();
                }
            }
        });
    }
}
