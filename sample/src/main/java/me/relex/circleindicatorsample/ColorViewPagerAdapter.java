package me.relex.circleindicatorsample;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Simple adapter used to adapt {@link ColorView} inside a view pager.
 */
public class ColorViewPagerAdapter extends PagerAdapter {

    /**
     * Colors used as model for each {@link ColorView} inside the view pager.
     */
    private ArrayList<Integer> colors;

    /**
     * Simple adapter used to adapt {@link ColorView} inside a view pager.
     *
     * @param colorArray colors displayed inside the view pager.
     */
    public ColorViewPagerAdapter(@NonNull ArrayList<Integer> colorArray) {
        this.colors = colorArray;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ColorView colorView = new ColorView(container.getContext());
        colorView.setBackgroundColor(colors.get(position));
        container.addView(colorView);
        return colorView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((ColorView) object));
    }

    @Override
    public int getCount() {
        return this.colors.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
