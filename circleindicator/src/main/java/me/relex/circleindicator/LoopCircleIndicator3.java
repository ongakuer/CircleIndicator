package me.relex.circleindicator;

import android.content.Context;
import android.util.AttributeSet;

public class LoopCircleIndicator3 extends CircleIndicator3 {

	public LoopCircleIndicator3(Context context) {
		super(context);
	}

	public LoopCircleIndicator3(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LoopCircleIndicator3(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public LoopCircleIndicator3(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public void createIndicators(int count, int currentPosition) {
		int realIndicatorsCount = count - 2;
		if (realIndicatorsCount >= 0) {
			super.createIndicators(realIndicatorsCount, currentPosition - 1);
		} else {
			super.createIndicators(count, currentPosition);
		}
	}

	@Override
	public void animatePageSelected(int position) {
		if (mLastPosition > 0) mLastPosition -= 1;
		super.animatePageSelected(position - 1);
		mLastPosition = position;
	}
}
