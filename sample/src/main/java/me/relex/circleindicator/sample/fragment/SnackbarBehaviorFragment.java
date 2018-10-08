package me.relex.circleindicator.sample.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.sample.R;
import me.relex.circleindicator.sample.SamplePagerAdapter;

public class SnackbarBehaviorFragment extends Fragment {

    private Snackbar mSnackbar;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample_snackbar_behavior, container, false);
    }

    @Override public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        ViewPager viewpager = view.findViewById(R.id.viewpager);
        CircleIndicator indicator = view.findViewById(R.id.indicator);
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
