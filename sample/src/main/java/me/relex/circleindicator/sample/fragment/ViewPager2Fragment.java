package me.relex.circleindicator.sample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import me.relex.circleindicator.CircleIndicator3;
import me.relex.circleindicator.sample.R;
import me.relex.circleindicator.sample.SampleRecyclerAdapter;

public class ViewPager2Fragment extends Fragment {

    private SampleRecyclerAdapter mAdapter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample_viewpager2, container, false);
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAdapter = new SampleRecyclerAdapter(5);

        ViewPager2 viewpager = view.findViewById(R.id.viewpager);
        viewpager.setAdapter(mAdapter);

        // CircleIndicator3 for RecyclerView
        CircleIndicator3 indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(viewpager);

        // CurrentItem
        viewpager.setCurrentItem(2,false);

        // Observe Data Change
        mAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mAdapter.add();
            }
        });
        view.findViewById(R.id.remove).setOnClickListener(v -> {
            mAdapter.remove();
        });
    }
}
