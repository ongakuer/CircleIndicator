package me.relex.circleindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import com.nineoldandroids.animation.AnimatorInflater;
import com.nineoldandroids.animation.AnimatorSet;

import static android.support.v4.view.ViewPager.OnPageChangeListener;

public class CircleIndicator extends LinearLayout implements OnPageChangeListener {

    private final static int DEFAULT_INDICATOR_WIDTH = 5;

    private ViewPager mViewpager;

    private OnPageChangeListener mViewPagerOnPageChangeListener;

    private int mIndicatorMargin;

    private int mIndicatorWidth;

    private int mAnimatorResId = R.animator.scale_with_alpha;

    private int mIndicatorBackground = R.drawable.white_radius;

    private int mCurrentPosition = 0;

    private AnimatorSet mAnimationOut;
    private AnimatorSet mAnimationIn;

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

        mAnimationOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, mAnimatorResId);
        mAnimationOut.setInterpolator(new LinearInterpolator());
        mAnimationIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, mAnimatorResId);
        mAnimationIn.setInterpolator(new ReverseInterpolator());
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray =
                    context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
            mIndicatorWidth =
                    typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
            mIndicatorMargin =
                    typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_margin, -1);
            mAnimatorResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator,
                    R.animator.scale_with_alpha);
            mIndicatorBackground = typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable,
                    R.drawable.white_radius);
            typedArray.recycle();
        }

        mIndicatorMargin =
                (mIndicatorMargin == -1) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorMargin;
        mIndicatorWidth =
                (mIndicatorWidth == -1) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorWidth;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewpager = viewPager;
        createIndicators(viewPager);
        mViewpager.setOnPageChangeListener(this);
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {

        if (mViewpager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        }

        mViewPagerOnPageChangeListener = onPageChangeListener;
        mViewpager.setOnPageChangeListener(this);
    }

    @Override public void onPageScrolled(int position, float positionOffset,
            int positionOffsetPixels) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageScrolled(position, positionOffset,
                    positionOffsetPixels);
        }
    }

    @Override public void onPageSelected(int position) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageSelected(position);
        }

        mAnimationIn.setTarget(getChildAt(mCurrentPosition));
        mAnimationIn.start();

        mAnimationOut.setTarget(getChildAt(position));
        mAnimationOut.start();

        mCurrentPosition = position;
    }

    @Override public void onPageScrollStateChanged(int state) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    private void createIndicators(ViewPager viewPager) {
        removeAllViews();
        int count = viewPager.getAdapter().getCount();
        if (count <= 0) {
            return;
        }

        for (int i = 0; i < count; i++) {
            View Indicator = new View(getContext());
            Indicator.setBackgroundResource(mIndicatorBackground);
            addView(Indicator, mIndicatorWidth, mIndicatorWidth);
            LayoutParams lp = (LayoutParams) Indicator.getLayoutParams();
            lp.leftMargin = mIndicatorMargin;
            lp.rightMargin = mIndicatorMargin;
            Indicator.setLayoutParams(lp);

            mAnimationOut.setTarget(Indicator);
            mAnimationOut.start();
        }

        mAnimationOut.setTarget(getChildAt(mCurrentPosition));
        mAnimationOut.start();
    }

    private class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float input) {
            return Math.abs(input - 1.0f);
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
