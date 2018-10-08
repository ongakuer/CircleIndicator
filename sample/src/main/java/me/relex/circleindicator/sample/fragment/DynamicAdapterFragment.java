package me.relex.circleindicator.sample.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.sample.R;
import me.relex.circleindicator.sample.SamplePagerAdapter;

public class DynamicAdapterFragment extends Fragment implements View.OnClickListener {

    private SamplePagerAdapter mAdapter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample_dynamic_adapter, container, false);
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        view.findViewById(R.id.add).setOnClickListener(this);
        view.findViewById(R.id.remove).setOnClickListener(this);

        mAdapter = new SamplePagerAdapter(1) {
            @Override public int getItemPosition(@NonNull Object object) {
                return POSITION_NONE;
            }
        };

        ViewPager viewpager = view.findViewById(R.id.viewpager);
        CircleIndicator indicator = view.findViewById(R.id.indicator);
        viewpager.setAdapter(mAdapter);
        indicator.setViewPager(viewpager);
        mAdapter.registerDataSetObserver(indicator.getDataSetObserver());
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                mAdapter.addItem();
                break;
            case R.id.remove:
                mAdapter.removeItem();
                break;
            default:
                break;
        }
    }
}
