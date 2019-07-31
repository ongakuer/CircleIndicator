package me.relex.circleindicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

/**
 * CircleIndicator work with ViewPager2
 */
public class CircleIndicator3 extends BaseCircleIndicator {

    private ViewPager2 mViewpager;

    public CircleIndicator3(Context context) {
        super(context);
    }

    public CircleIndicator3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleIndicator3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleIndicator3(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setViewPager(@Nullable ViewPager2 viewPager) {
        mViewpager = viewPager;
        if (mViewpager != null && mViewpager.getAdapter() != null) {
            mLastPosition = -1;
            createIndicators();
            mViewpager.unregisterOnPageChangeCallback(mInternalPageChangeCallback);
            mViewpager.registerOnPageChangeCallback(mInternalPageChangeCallback);
            mInternalPageChangeCallback.onPageSelected(mViewpager.getCurrentItem());
        }
    }

    private void createIndicators() {
        RecyclerView.Adapter adapter = mViewpager.getAdapter();
        int count;
        if (adapter == null) {
            count = 0;
        } else {
            count = adapter.getItemCount();
        }
        createIndicators(count, mViewpager.getCurrentItem());
    }

    private final ViewPager2.OnPageChangeCallback mInternalPageChangeCallback =
            new ViewPager2.OnPageChangeCallback() {
                @Override public void onPageSelected(int position) {
                    if (position == mLastPosition
                            || mViewpager.getAdapter() == null
                            || mViewpager.getAdapter().getItemCount() <= 0) {
                        return;
                    }
                    animatePageSelected(position);
                }
            };

    private final RecyclerView.AdapterDataObserver mAdapterDataObserver =
            new RecyclerView.AdapterDataObserver() {
                @Override public void onChanged() {
                    super.onChanged();
                    if (mViewpager == null) {
                        return;
                    }
                    RecyclerView.Adapter adapter = mViewpager.getAdapter();
                    int newCount = adapter != null ? adapter.getItemCount() : 0;
                    int currentCount = getChildCount();
                    if (newCount == currentCount) {
                        // No change
                        return;
                    } else if (mLastPosition < newCount) {
                        mLastPosition = mViewpager.getCurrentItem();
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
