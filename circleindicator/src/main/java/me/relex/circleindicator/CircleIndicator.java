package me.relex.circleindicator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.annotation.AnimatorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import static android.support.v4.view.ViewPager.OnPageChangeListener;

public class CircleIndicator extends LinearLayout implements OnPageChangeListener {

    private final static int DEFAULT_INDICATOR_WIDTH = 5;
    private ViewPager mViewpager;
    private int mIndicatorMargin = -1;
    private int mIndicatorWidth = -1;
    private int mIndicatorHeight = -1;
    private int mAnimatorResId = R.animator.scale_with_alpha;
    private int mRemoveAnimatorResId = R.animator.default_remove;
    private int mInsertAnimatorResId = R.animator.default_insert;
    private int mAnimatorReverseResId = 0;
    private int mIndicatorBackgroundResId = R.drawable.white_radius;
    private int mIndicatorUnselectedBackgroundResId = R.drawable.white_radius;
    private int mCurrentPosition = 0;
    private Animator mAnimationOut;
    private Animator mAnimationIn;
    private Animator mAnimationRemove;
    private Animator mAnimationInsert;
    private View mIndicatorToRemove;

    // data set observer used to catch add / remove event.
    private DataSetObserver internalViewPagerDataSetObserver;

    public CircleIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        handleTypedArray(context, attrs);
        checkIndicatorConfig(context);

        // add layout transition to animate insertion and removal without item animator.
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setAnimator(LayoutTransition.APPEARING, null);
        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, null);
        setLayoutTransition(layoutTransition);

        // view pager data set observer used to catch insertion and removal events.
        internalViewPagerDataSetObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int newPageCount = mViewpager.getAdapter().getCount();
                if (newPageCount < getChildCount()) {
                    onPageRemoved(newPageCount);
                } else if (newPageCount > getChildCount()) {
                    onPageInserted(newPageCount);
                }
            }
        };
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
        mIndicatorWidth =
                typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
        mIndicatorHeight =
                typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_height, -1);
        mIndicatorMargin =
                typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_margin, -1);
        mAnimatorResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator,
                R.animator.scale_with_alpha);
        mAnimatorReverseResId =
                typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator_reverse, 0);
        mIndicatorBackgroundResId =
                typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable,
                        R.drawable.white_radius);
        mIndicatorUnselectedBackgroundResId =
                typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable_unselected,
                        mIndicatorBackgroundResId);
        mRemoveAnimatorResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator_remove,
                mRemoveAnimatorResId);
        mInsertAnimatorResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator_insert,
                mInsertAnimatorResId);

        typedArray.recycle();
    }

    /**
     * Create and configure Indicator in Java code.
     */
    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin) {
        configureIndicator(indicatorWidth, indicatorHeight, indicatorMargin,
                R.animator.scale_with_alpha,
                0,
                R.animator.default_insert,
                R.animator.default_remove,
                R.drawable.white_radius,
                R.drawable.white_radius);
    }

    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin,
                                   @AnimatorRes int animatorId,
                                   @AnimatorRes int animatorReverseId,
                                   @AnimatorRes int animatorInsertId,
                                   @AnimatorRes int animatorRemoveId,
                                   @DrawableRes int indicatorBackgroundId,
                                   @DrawableRes int indicatorUnselectedBackgroundId) {

        mIndicatorWidth = indicatorWidth;
        mIndicatorHeight = indicatorHeight;
        mIndicatorMargin = indicatorMargin;

        mAnimatorResId = animatorId;
        mAnimatorReverseResId = animatorReverseId;
        mInsertAnimatorResId = animatorInsertId;
        mRemoveAnimatorResId = animatorRemoveId;
        mIndicatorBackgroundResId = indicatorBackgroundId;
        mIndicatorUnselectedBackgroundResId = indicatorUnselectedBackgroundId;

        checkIndicatorConfig(getContext());
    }

    private void checkIndicatorConfig(Context context) {
        mIndicatorWidth = (mIndicatorWidth < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorWidth;
        mIndicatorHeight =
                (mIndicatorHeight < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorHeight;
        mIndicatorMargin =
                (mIndicatorMargin < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorMargin;

        mAnimatorResId = (mAnimatorResId == 0) ? R.animator.scale_with_alpha : mAnimatorResId;
        mAnimationOut = AnimatorInflater.loadAnimator(context, mAnimatorResId);

        mAnimationRemove = AnimatorInflater.loadAnimator(context, mRemoveAnimatorResId);
        mAnimationRemove.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(mIndicatorToRemove);
                mIndicatorToRemove = null;
            }
        });

        mAnimationInsert = AnimatorInflater.loadAnimator(context, mInsertAnimatorResId);

        if (mAnimatorReverseResId == 0) {
            mAnimationIn = AnimatorInflater.loadAnimator(context, mAnimatorResId);
            mAnimationIn.setInterpolator(new ReverseInterpolator());
        } else {
            mAnimationIn = AnimatorInflater.loadAnimator(context, mAnimatorReverseResId);
        }
        mIndicatorBackgroundResId = (mIndicatorBackgroundResId == 0) ? R.drawable.white_radius
                : mIndicatorBackgroundResId;
        mIndicatorUnselectedBackgroundResId =
                (mIndicatorUnselectedBackgroundResId == 0) ? mIndicatorBackgroundResId
                        : mIndicatorUnselectedBackgroundResId;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewpager = viewPager;
        mCurrentPosition = mViewpager.getCurrentItem();
        createIndicators(viewPager);
        mViewpager.removeOnPageChangeListener(this);
        mViewpager.addOnPageChangeListener(this);
        onPageSelected(mCurrentPosition);
        mViewpager.getAdapter().registerDataSetObserver(internalViewPagerDataSetObserver);
    }

    /**
     * @deprecated User ViewPager addOnPageChangeListener
     */
    @Deprecated
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        if (mViewpager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        }
        mViewpager.removeOnPageChangeListener(onPageChangeListener);
        mViewpager.addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        if (mViewpager.getAdapter() == null || mViewpager.getAdapter().getCount() <= 0) {
            return;
        }

        if (mAnimationIn.isRunning()) mAnimationIn.end();
        if (mAnimationOut.isRunning()) mAnimationOut.end();

        View currentIndicator = getChildAt(mCurrentPosition);
        currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
        mAnimationIn.setTarget(currentIndicator);
        mAnimationIn.start();

        View selectedIndicator = getChildAt(position);
        selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId);
        mAnimationOut.setTarget(selectedIndicator);
        mAnimationOut.start();

        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void createIndicators(ViewPager viewPager) {
        removeAllViews();
        if (viewPager.getAdapter() == null) {
            return;
        }

        int count = viewPager.getAdapter().getCount();
        if (count <= 0) {
            return;
        }
        addIndicator(mIndicatorBackgroundResId, mAnimationOut);
        for (int i = 1; i < count; i++) {
            addIndicator(mIndicatorUnselectedBackgroundResId, mAnimationIn);
        }
    }

    private void addIndicator(@DrawableRes int backgroundDrawableId, Animator animator) {
        if (animator.isRunning()) animator.end();

        View Indicator = new View(getContext());
        Indicator.setBackgroundResource(backgroundDrawableId);
        addView(Indicator, mIndicatorWidth, mIndicatorHeight);
        LayoutParams lp = (LayoutParams) Indicator.getLayoutParams();
        lp.leftMargin = mIndicatorMargin;
        lp.rightMargin = mIndicatorMargin;
        Indicator.setLayoutParams(lp);

        animator.setTarget(Indicator);
        animator.start();
    }

    private void onPageRemoved(int newPageCount) {
        if (mAnimationRemove.isRunning()) {
            mAnimationRemove.end();
            if (newPageCount == this.getChildCount()) {
                return;
            }
        }

        if (mCurrentPosition == newPageCount) {
            mCurrentPosition--;
        }

        mIndicatorToRemove = getChildAt(getChildCount() - 1);
        mAnimationRemove.setTarget(mIndicatorToRemove);
        mAnimationRemove.start();
    }

    private void onPageInserted(int newPageCount) {
        if (newPageCount == 1) {
            mCurrentPosition = 0;
            addIndicator(mIndicatorBackgroundResId, mAnimationOut);
        } else {
            addIndicator(mIndicatorUnselectedBackgroundResId, mAnimationInsert);
        }
    }

    private class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
