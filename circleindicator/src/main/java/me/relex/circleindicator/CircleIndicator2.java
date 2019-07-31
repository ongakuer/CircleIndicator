package me.relex.circleindicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

/**
 * CircleIndicator2 work with RecyclerView and SnapHelper
 */
public class CircleIndicator2 extends BaseCircleIndicator {

    private RecyclerView mRecyclerView;
    private SnapHelper mSnapHelper;

    public CircleIndicator2(Context context) {
        super(context);
    }

    public CircleIndicator2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleIndicator2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleIndicator2(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void attachToRecyclerView(@NonNull RecyclerView recyclerView,
            @NonNull SnapHelper snapHelper) {
        mRecyclerView = recyclerView;
        mSnapHelper = snapHelper;
        mLastPosition = -1;
        createIndicators();
        recyclerView.removeOnScrollListener(mInternalOnScrollListener);
        recyclerView.addOnScrollListener(mInternalOnScrollListener);
    }

    private void createIndicators() {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        int count;
        if (adapter == null) {
            count = 0;
        } else {
            count = adapter.getItemCount();
        }
        createIndicators(count, getSnapPosition(mRecyclerView.getLayoutManager()));
    }

    public int getSnapPosition(@Nullable RecyclerView.LayoutManager layoutManager) {
        if (layoutManager == null) {
            return RecyclerView.NO_POSITION;
        }
        View snapView = mSnapHelper.findSnapView(layoutManager);
        if (snapView == null) {
            return RecyclerView.NO_POSITION;
        }
        return layoutManager.getPosition(snapView);
    }

    private final RecyclerView.OnScrollListener mInternalOnScrollListener =
            new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    int position = getSnapPosition(recyclerView.getLayoutManager());
                    if (position == RecyclerView.NO_POSITION) {
                        return;
                    }
                    animatePageSelected(position);
                }
            };

    private final RecyclerView.AdapterDataObserver mAdapterDataObserver =
            new RecyclerView.AdapterDataObserver() {
                @Override public void onChanged() {
                    super.onChanged();
                    if (mRecyclerView == null) {
                        return;
                    }
                    RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
                    int newCount = adapter != null ? adapter.getItemCount() : 0;
                    int currentCount = getChildCount();
                    if (newCount == currentCount) {
                        // No change
                        return;
                    } else if (mLastPosition < newCount) {
                        mLastPosition = getSnapPosition(mRecyclerView.getLayoutManager());
                    } else {
                        mLastPosition = RecyclerView.NO_POSITION;
                    }
                    createIndicators();
                }

                @Override public void onItemRangeChanged(int positionStart, int itemCount) {
                    super.onItemRangeChanged(positionStart, itemCount);
                    onChanged();
                }

                @Override public void onItemRangeChanged(int positionStart, int itemCount,
                        @Nullable Object payload) {
                    super.onItemRangeChanged(positionStart, itemCount, payload);
                    onChanged();
                }

                @Override public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    onChanged();
                }

                @Override public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    onChanged();
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                    onChanged();
                }
            };

    public RecyclerView.AdapterDataObserver getAdapterDataObserver() {
        return mAdapterDataObserver;
    }
}
