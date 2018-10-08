/*
 * Copyright (C) 2013 Leszek Mzyk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.imbryk.viewPager;

import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

public class LoopPagerAdapterWrapper extends PagerAdapter {

    private final PagerAdapter mAdapter;

    private SparseArray<ToDestroy> mToDestroy = new SparseArray<>();

    private static final boolean DEFAULT_BOUNDARY_CASHING = true;
    private static final boolean DEFAULT_BOUNDARY_LOOPING = true;

    private boolean mBoundaryCaching = DEFAULT_BOUNDARY_CASHING;
    private boolean mBoundaryLooping = DEFAULT_BOUNDARY_LOOPING;

    void setBoundaryCaching(boolean flag) {
        mBoundaryCaching = flag;
    }

    void setBoundaryLooping(boolean flag) {
        mBoundaryLooping = flag;
    }

    LoopPagerAdapterWrapper(PagerAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override public void notifyDataSetChanged() {
        mToDestroy = new SparseArray<>();
        super.notifyDataSetChanged();
    }

    int toRealPosition(int position) {
        int realPosition = position;
        int realCount = getRealCount();
        if (realCount == 0) {
            return 0;
        }
        if (mBoundaryLooping) {
            realPosition = (position - 1) % realCount;
            if (realPosition < 0) {
                realPosition += realCount;
            }
        }

        return realPosition;
    }

    public int toInnerPosition(int realPosition) {
        int position = (realPosition + 1);
        return mBoundaryLooping ? position : realPosition;
    }

    private int getRealFirstPosition() {
        return mBoundaryLooping ? 1 : 0;
    }

    private int getRealLastPosition() {
        return getRealFirstPosition() + getRealCount() - 1;
    }

    @Override public int getCount() {
        int count = getRealCount();
        return mBoundaryLooping ? count + 2 : count;
    }

    public int getRealCount() {
        return mAdapter.getCount();
    }

    public PagerAdapter getRealAdapter() {
        return mAdapter;
    }

    @NonNull @Override public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = (mAdapter instanceof FragmentPagerAdapter
                || mAdapter instanceof FragmentStatePagerAdapter) ? position
                : toRealPosition(position);

        if (mBoundaryCaching) {
            ToDestroy toDestroy = mToDestroy.get(position);
            if (toDestroy != null) {
                mToDestroy.remove(position);
                return toDestroy.object;
            }
        }
        return mAdapter.instantiateItem(container, realPosition);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        int realFirst = getRealFirstPosition();
        int realLast = getRealLastPosition();
        int realPosition = (mAdapter instanceof FragmentPagerAdapter
                || mAdapter instanceof FragmentStatePagerAdapter) ? position
                : toRealPosition(position);

        if (mBoundaryCaching && (position == realFirst || position == realLast)) {
            mToDestroy.put(position, new ToDestroy(container, realPosition, object));
        } else {
            mAdapter.destroyItem(container, realPosition, object);
        }
    }

    /*
     * Delegate rest of methods directly to the inner adapter.
     */

    @Override public void finishUpdate(@NonNull ViewGroup container) {
        mAdapter.finishUpdate(container);
    }

    @Override public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return mAdapter.isViewFromObject(view, object);
    }

    @Override public void restoreState(Parcelable bundle, ClassLoader classLoader) {
        mAdapter.restoreState(bundle, classLoader);
    }

    @Override public Parcelable saveState() {
        return mAdapter.saveState();
    }

    @Override public void startUpdate(@NonNull ViewGroup container) {
        mAdapter.startUpdate(container);
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        mAdapter.setPrimaryItem(container, position, object);
    }

    /*
     * End delegation
     */

    /**
     * Container class for caching the boundary views
     */
    static class ToDestroy {
        final ViewGroup container;
        final int position;
        final Object object;

        ToDestroy(ViewGroup container, int position, Object object) {
            this.container = container;
            this.position = position;
            this.object = object;
        }
    }
}