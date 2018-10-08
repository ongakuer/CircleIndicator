package me.relex.circleindicator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import androidx.annotation.AnimatorRes;
import androidx.annotation.DrawableRes;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class CircleIndicator extends LinearLayout {

    private final static int DEFAULT_INDICATOR_WIDTH = 5;
    private ViewPager mViewpager;
    private int mIndicatorMargin = -1;
    private int mIndicatorWidth = -1;
    private int mIndicatorHeight = -1;

    private int mIndicatorBackgroundResId;
    private int mIndicatorUnselectedBackgroundResId;

    private Animator mAnimatorOut;
    private Animator mAnimatorIn;
    private Animator mImmediateAnimatorOut;
    private Animator mImmediateAnimatorIn;

    private int mLastPosition = -1;

    public CircleIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Config config = handleTypedArray(context, attrs);
        initialize(config);
    }

    public static class Config {
        private int width = -1;
        private int height = -1;
        private int margin = -1;
        @AnimatorRes private int animatorResId = R.animator.scale_with_alpha;
        @AnimatorRes private int animatorReverseResId = 0;
        @DrawableRes private int backgroundResId = R.drawable.white_radius;
        @DrawableRes private int unselectedBackgroundId;
        private int orientation = HORIZONTAL;
        private int gravity = Gravity.CENTER;

        public Config width(int width) {
            this.width = width;
            return this;
        }

        public Config height(int height) {
            this.height = height;
            return this;
        }

        public Config margin(int margin) {
            this.margin = margin;
            return this;
        }

        public Config animator(@AnimatorRes int animatorResId) {
            this.animatorResId = animatorResId;
            return this;
        }

        public Config animatorReverse(@AnimatorRes int animatorReverseResId) {
            this.animatorReverseResId = animatorReverseResId;
            return this;
        }

        public Config drawable(@DrawableRes int backgroundResId) {
            this.backgroundResId = backgroundResId;
            return this;
        }

        public Config drawableUnselected(@DrawableRes int unselectedBackgroundId) {
            this.unselectedBackgroundId = unselectedBackgroundId;
            return this;
        }

        public Config orientation(int orientation) {
            this.orientation = orientation;
            return this;
        }

        public Config gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Config build() {
            return this;
        }
    }

    private Config handleTypedArray(Context context, AttributeSet attrs) {
        Config config = new Config();
        if (attrs == null) {
            return config;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
        config.width = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
        config.height = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_height, -1);
        config.margin = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_margin, -1);
        config.animatorResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator,
                R.animator.scale_with_alpha);
        config.animatorReverseResId =
                typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator_reverse, 0);
        config.backgroundResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable,
                R.drawable.white_radius);
        config.unselectedBackgroundId =
                typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable_unselected,
                        config.backgroundResId);
        config.orientation = typedArray.getInt(R.styleable.CircleIndicator_ci_orientation, -1);
        config.gravity = typedArray.getInt(R.styleable.CircleIndicator_ci_gravity, -1);
        typedArray.recycle();
        return config.build();
    }

    public void initialize(Config config) {
        int miniSize = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_INDICATOR_WIDTH, getResources().getDisplayMetrics()) + 0.5f);
        mIndicatorWidth = (config.width < 0) ? miniSize : config.width;
        mIndicatorHeight = (config.height < 0) ? miniSize : config.height;
        mIndicatorMargin = (config.margin < 0) ? miniSize : config.margin;

        mAnimatorOut = createAnimatorOut(config);
        mImmediateAnimatorOut = createAnimatorOut(config);
        mImmediateAnimatorOut.setDuration(0);

        mAnimatorIn = createAnimatorIn(config);
        mImmediateAnimatorIn = createAnimatorIn(config);
        mImmediateAnimatorIn.setDuration(0);

        mIndicatorBackgroundResId =
                (config.backgroundResId == 0) ? R.drawable.white_radius : config.backgroundResId;
        mIndicatorUnselectedBackgroundResId =
                (config.unselectedBackgroundId == 0) ? config.backgroundResId
                        : config.unselectedBackgroundId;

        setOrientation(config.orientation == VERTICAL ? VERTICAL : HORIZONTAL);
        setGravity(config.gravity >= 0 ? config.gravity : Gravity.CENTER);
    }

    private Animator createAnimatorOut(Config config) {
        return AnimatorInflater.loadAnimator(getContext(), config.animatorResId);
    }

    private Animator createAnimatorIn(Config config) {

        Animator animatorIn;
        if (config.animatorReverseResId == 0) {
            animatorIn = AnimatorInflater.loadAnimator(getContext(), config.animatorResId);
            animatorIn.setInterpolator(new ReverseInterpolator());
        } else {
            animatorIn = AnimatorInflater.loadAnimator(getContext(), config.animatorReverseResId);
        }
        return animatorIn;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewpager = viewPager;
        if (mViewpager != null && mViewpager.getAdapter() != null) {
            mLastPosition = -1;
            createIndicators();
            mViewpager.removeOnPageChangeListener(mInternalPageChangeListener);
            mViewpager.addOnPageChangeListener(mInternalPageChangeListener);
            mInternalPageChangeListener.onPageSelected(mViewpager.getCurrentItem());
        }
    }

    private final ViewPager.OnPageChangeListener mInternalPageChangeListener =
            new ViewPager.OnPageChangeListener() {

                @Override public void onPageScrolled(int position, float positionOffset,
                        int positionOffsetPixels) {
                }

                @Override public void onPageSelected(int position) {

                    if (mViewpager.getAdapter() == null
                            || mViewpager.getAdapter().getCount() <= 0) {
                        return;
                    }

                    if (mAnimatorIn.isRunning()) {
                        mAnimatorIn.end();
                        mAnimatorIn.cancel();
                    }

                    if (mAnimatorOut.isRunning()) {
                        mAnimatorOut.end();
                        mAnimatorOut.cancel();
                    }

                    View currentIndicator;
                    if (mLastPosition >= 0
                            && (currentIndicator = getChildAt(mLastPosition)) != null) {
                        currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
                        mAnimatorIn.setTarget(currentIndicator);
                        mAnimatorIn.start();
                    }

                    View selectedIndicator = getChildAt(position);
                    if (selectedIndicator != null) {
                        selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId);
                        mAnimatorOut.setTarget(selectedIndicator);
                        mAnimatorOut.start();
                    }
                    mLastPosition = position;
                }

                @Override public void onPageScrollStateChanged(int state) {
                }
            };

    public DataSetObserver getDataSetObserver() {
        return mInternalDataSetObserver;
    }

    private final DataSetObserver mInternalDataSetObserver = new DataSetObserver() {
        @Override public void onChanged() {
            super.onChanged();
            if (mViewpager == null) {
                return;
            }
            PagerAdapter adapter = mViewpager.getAdapter();
            int newCount = adapter != null ? adapter.getCount() : 0;
            int currentCount = getChildCount();
            if (newCount == currentCount) {
                // No change
                return;
            } else if (mLastPosition < newCount) {
                mLastPosition = mViewpager.getCurrentItem();
            } else {
                mLastPosition = -1;
            }

            createIndicators();
        }
    };

    /**
     * @deprecated User ViewPager addOnPageChangeListener
     */
    @Deprecated public void setOnPageChangeListener(
            ViewPager.OnPageChangeListener onPageChangeListener) {
        if (mViewpager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        }
        mViewpager.removeOnPageChangeListener(onPageChangeListener);
        mViewpager.addOnPageChangeListener(onPageChangeListener);
    }

    private void createIndicators() {
        removeAllViews();
        PagerAdapter adapter = mViewpager.getAdapter();
        int count;
        if (adapter == null || (count = adapter.getCount()) <= 0) {
            return;
        }
        int currentItem = mViewpager.getCurrentItem();
        int orientation = getOrientation();

        for (int i = 0; i < count; i++) {
            if (currentItem == i) {
                addIndicator(orientation, mIndicatorBackgroundResId, mImmediateAnimatorOut);
            } else {
                addIndicator(orientation, mIndicatorUnselectedBackgroundResId,
                        mImmediateAnimatorIn);
            }
        }
    }

    private void addIndicator(int orientation, @DrawableRes int backgroundDrawableId,
            Animator animator) {

        if (animator.isRunning()) {
            animator.end();
            animator.cancel();
        }

        View indicator = new View(getContext());
        indicator.setBackgroundResource(backgroundDrawableId);
        addView(indicator, mIndicatorWidth, mIndicatorHeight);
        LayoutParams lp = (LayoutParams) indicator.getLayoutParams();

        if (orientation == HORIZONTAL) {
            lp.leftMargin = mIndicatorMargin;
            lp.rightMargin = mIndicatorMargin;
        } else {
            lp.topMargin = mIndicatorMargin;
            lp.bottomMargin = mIndicatorMargin;
        }

        indicator.setLayoutParams(lp);

        animator.setTarget(indicator);
        animator.start();
    }

    private class ReverseInterpolator implements Interpolator {
        @Override public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }
}
