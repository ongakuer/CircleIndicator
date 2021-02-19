package me.relex.circleindicator;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

class SimpleSnapIndexController implements SnapIndexController {

    private final RecyclerView mRecyclerView;
    private final SnapHelper mSnapHelper;

    public SimpleSnapIndexController(RecyclerView recyclerView, SnapHelper snapHelper) {
        mRecyclerView = recyclerView;
        mSnapHelper = snapHelper;
    }

    @Override
    public int getSnappedIndex() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager == null) return RecyclerView.NO_POSITION;

        final View snapView = mSnapHelper.findSnapView(layoutManager);
        if (snapView == null) return RecyclerView.NO_POSITION;

        return layoutManager.getPosition(snapView);
    }

    @Override
    public int getTotalIndicatorCount() {
        final RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter == null) return 0;
        return adapter.getItemCount();
    }
}
