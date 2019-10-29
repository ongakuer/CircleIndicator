package me.relex.circleindicator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

class BaseCircleIndicator extends LinearLayout {

    private final static int DEFAULT_INDICATOR_WIDTH = 5;

    protected int mIndicatorMargin = -1;
    protected int mIndicatorWidth = -1;
    protected int mIndicatorHeight = -1;

    protected int mIndicatorBackgroundResId;
    protected int mIndicatorUnselectedBackgroundResId;

    protected int mLastIndicatorBackgroundResId;
    protected int mLastIndicatorUnselectedBackgroundResId;

    protected Animator mAnimatorOut;
    protected Animator mAnimatorIn;
    protected Animator mImmediateAnimatorOut;
    protected Animator mImmediateAnimatorIn;

    protected int mLastPosition = -1;

    @Nullable private IndicatorCreatedListener mIndicatorCreatedListener;

    public BaseCircleIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public BaseCircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseCircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseCircleIndicator(Context context, AttributeSet attrs, int defStyleAttr,
                               int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Config config = handleTypedArray(context, attrs);
        initialize(config);

        if (isInEditMode()) {
            createIndicators(3, 1);
        }
    }

    private Config handleTypedArray(Context context, AttributeSet attrs) {
        Config config = new Config();
        if (attrs == null) {
            return config;
        }
        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.BaseCircleIndicator);
        config.width =
                typedArray.getDimensionPixelSize(R.styleable.BaseCircleIndicator_ci_width, -1);
        config.height =
                typedArray.getDimensionPixelSize(R.styleable.BaseCircleIndicator_ci_height, -1);
        config.margin =
                typedArray.getDimensionPixelSize(R.styleable.BaseCircleIndicator_ci_margin, -1);
        config.animatorResId = typedArray.getResourceId(R.styleable.BaseCircleIndicator_ci_animator,
                R.animator.scale_with_alpha);
        config.animatorReverseResId =
                typedArray.getResourceId(R.styleable.BaseCircleIndicator_ci_animator_reverse, 0);
        config.backgroundResId =
                typedArray.getResourceId(R.styleable.BaseCircleIndicator_ci_drawable,
                        R.drawable.white_radius);
        config.unselectedBackgroundId =
                typedArray.getResourceId(R.styleable.BaseCircleIndicator_ci_drawable_unselected,
                        config.backgroundResId);
        config.orientation = typedArray.getInt(R.styleable.BaseCircleIndicator_ci_orientation, -1);
        config.gravity = typedArray.getInt(R.styleable.BaseCircleIndicator_ci_gravity, -1);
        config.backgroundLastResID = typedArray.getResourceId(R.styleable.BaseCircleIndicator_ci_drawable_last, 0);
        config.unselectedBackgroundLastId = typedArray.getResourceId(R.styleable.BaseCircleIndicator_ci_drawable_last_unselected, 0);
        typedArray.recycle();

        return config;
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
        mLastIndicatorBackgroundResId = (config.backgroundLastResID == 0) ? config.backgroundResId : config.backgroundLastResID;
        mLastIndicatorUnselectedBackgroundResId =
                (config.unselectedBackgroundLastId == 0) ?
                        (config.unselectedBackgroundId == 0) ?
                                config.backgroundResId : config.unselectedBackgroundId :
                        config.unselectedBackgroundLastId;

        setOrientation(config.orientation == VERTICAL ? VERTICAL : HORIZONTAL);
        setGravity(config.gravity >= 0 ? config.gravity : Gravity.CENTER);
    }

    public interface IndicatorCreatedListener {
        /**
         * IndicatorCreatedListener
         *
         * @param view internal indicator view
         * @param position position
         */
        void onIndicatorCreated(View view, int position);
    }

    public void setIndicatorCreatedListener(
            @Nullable IndicatorCreatedListener indicatorCreatedListener) {
        mIndicatorCreatedListener = indicatorCreatedListener;
    }

    protected Animator createAnimatorOut(Config config) {
        return AnimatorInflater.loadAnimator(getContext(), config.animatorResId);
    }

    protected Animator createAnimatorIn(Config config) {
        Animator animatorIn;
        if (config.animatorReverseResId == 0) {
            animatorIn = AnimatorInflater.loadAnimator(getContext(), config.animatorResId);
            animatorIn.setInterpolator(new ReverseInterpolator());
        } else {
            animatorIn = AnimatorInflater.loadAnimator(getContext(), config.animatorReverseResId);
        }
        return animatorIn;
    }

    public void createIndicators(int count, int currentPosition) {
        if (mImmediateAnimatorOut.isRunning()) {
            mImmediateAnimatorOut.end();
            mImmediateAnimatorOut.cancel();
        }

        if (mImmediateAnimatorIn.isRunning()) {
            mImmediateAnimatorIn.end();
            mImmediateAnimatorIn.cancel();
        }

        // Diff View
        int childViewCount = getChildCount();
        if (count < childViewCount) {
            removeViews(count, childViewCount - count);
        } else if (count > childViewCount) {
            int addCount = count - childViewCount;
            int orientation = getOrientation();
            for (int i = 0; i < addCount; i++) {
                addIndicator(orientation);
            }
        }

        // Bind Style
        View indicator;
        for (int i = 0; i < count; i++) {
            indicator = getChildAt(i);
            if (currentPosition == i) {
                indicator.setBackgroundResource(mIndicatorBackgroundResId);
                mImmediateAnimatorOut.setTarget(indicator);
                mImmediateAnimatorOut.start();
                mImmediateAnimatorOut.end();
                if(i == count - 1 ) {
                    indicator.setBackgroundResource(mLastIndicatorBackgroundResId);
                }
            } else {
                indicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
                if(i == count - 1 ) {
                    indicator.setBackgroundResource(mLastIndicatorUnselectedBackgroundResId);
                }
                mImmediateAnimatorIn.setTarget(indicator);
                mImmediateAnimatorIn.start();
                mImmediateAnimatorIn.end();
            }

            if (mIndicatorCreatedListener != null) {
                mIndicatorCreatedListener.onIndicatorCreated(indicator, i);
            }
        }

        mLastPosition = currentPosition;
    }

    protected void addIndicator(int orientation) {
        View indicator = new View(getContext());
        final LayoutParams params = generateDefaultLayoutParams();
        params.width = mIndicatorWidth;
        params.height = mIndicatorHeight;
        if (orientation == HORIZONTAL) {
            params.leftMargin = mIndicatorMargin;
            params.rightMargin = mIndicatorMargin;
        } else {
            params.topMargin = mIndicatorMargin;
            params.bottomMargin = mIndicatorMargin;
        }
        addView(indicator, params);
    }

    public void animatePageSelected(int position) {

        if (mLastPosition == position) {
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
        if (mLastPosition >= 0 && (currentIndicator = getChildAt(mLastPosition)) != null) {
            currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
            if(currentIndicator == getChildAt(getChildCount()-1)) {
                currentIndicator.setBackgroundResource(mLastIndicatorUnselectedBackgroundResId);
            }
            mAnimatorIn.setTarget(currentIndicator);
            mAnimatorIn.start();
        }

        View selectedIndicator = getChildAt(position);
        if (selectedIndicator != null) {
            selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId);
            if(selectedIndicator == getChildAt(getChildCount()-1)) {
                selectedIndicator.setBackgroundResource(mLastIndicatorBackgroundResId);
            }
            mAnimatorOut.setTarget(selectedIndicator);
            mAnimatorOut.start();
        }
        mLastPosition = position;
    }

    protected class ReverseInterpolator implements Interpolator {
        @Override public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }
}
