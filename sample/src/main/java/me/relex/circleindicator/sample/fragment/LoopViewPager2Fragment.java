package me.relex.circleindicator.sample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import me.relex.circleindicator.LoopCircleIndicator3;
import me.relex.circleindicator.sample.LoopRecycler2Adapter;
import me.relex.circleindicator.sample.R;

public class LoopViewPager2Fragment extends Fragment {

	private LoopRecycler2Adapter mAdapter;
	private ViewPager2 mViewPager;
	private LoopCircleIndicator3 mIndicator;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_sample_loop_viewpager2, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		mAdapter = new LoopRecycler2Adapter(3);

		mIndicator = view.findViewById(R.id.indicator);
		mViewPager = view.findViewById(R.id.viewpager);

		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(1, false);

		mAdapter.registerAdapterDataObserver(mIndicator.getAdapterDataObserver());

		mIndicator.setViewPager(mViewPager);
		mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				if (positionOffsetPixels == 0) {
					if (position == mAdapter.getItemCount() - 1) {
						mViewPager.setCurrentItem(1, false);
					}

					if (position == 0 && mAdapter.getItemCount() > 2) {
						mViewPager.setCurrentItem(mAdapter.getItemCount() - 2, false);
					}
				}
			}

			@Override
			public void onPageSelected(int position) {
				if (position == mAdapter.getItemCount() - 1) {
					mIndicator.animatePageSelected(1);
				}

				if (position == 0 && mAdapter.getItemCount() > 2) {
					mIndicator.animatePageSelected(mAdapter.getItemCount() - 2);
				}
			}
		});
	}

	@Override
	public void onDestroyView() {
		mAdapter.unregisterAdapterDataObserver(mIndicator.getAdapterDataObserver());
		super.onDestroyView();
	}
}