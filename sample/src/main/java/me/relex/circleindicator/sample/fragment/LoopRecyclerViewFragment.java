package me.relex.circleindicator.sample.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.relex.circleindicator.CircleIndicator2;
import me.relex.circleindicator.sample.LoopRecyclerAdapter;
import me.relex.circleindicator.sample.R;
import me.relex.recyclerpager.SnapPageScrollListener;

public class LoopRecyclerViewFragment extends Fragment {

    private LoopRecyclerAdapter mAdapter;
    private CircleIndicator2 mIndicator;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample_loop_recycer_view, container, false);
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAdapter = new LoopRecyclerAdapter(5);

        mIndicator = view.findViewById(R.id.indicator);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mIndicator.createIndicators(mAdapter.getRealItemCount(), 0);

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new SnapPageScrollListener() {
            @Override public void onPageSelected(int position) {
                mIndicator.animatePageSelected(mAdapter.getRealPosition(position));
            }

            @Override public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
                if (positionOffsetPixels == 0) {
                    recyclerView.scrollToPosition(mAdapter.getLoopPosition(position));
                }
            }
        });
    }
}
