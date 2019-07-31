package me.relex.circleindicator.sample;

import androidx.annotation.NonNull;

public class LoopRecyclerAdapter extends SampleRecyclerAdapter {
    public LoopRecyclerAdapter(int count) {
        super(count);
    }

    @Override public int getItemCount() {
        if (mCount > 1) {
            return mCount + 2;
        } else {
            return mCount;
        }
    }

    public int getRealItemCount() {
        return mCount;
    }

    public int getRealPosition(int position) {
        if (mCount <= 1) {
            return 0;
        } else {
            return position % mCount;
        }
    }

    public int getLoopPosition(int position) {
        int realPosition = getRealPosition(position);
        if (realPosition == 0) {
            return mCount;
        } else {
            return realPosition;
        }
    }

    @Override public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        holder.bindView(getRealPosition(position));
    }
}
