package me.relex.circleindicator;

import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

public class SnackbarBehavior extends CoordinatorLayout.Behavior<CircleIndicator> {

    public SnackbarBehavior() {
    }

    public SnackbarBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override public boolean layoutDependsOn(CoordinatorLayout parent, CircleIndicator child,
            View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override public boolean onDependentViewChanged(CoordinatorLayout parent, CircleIndicator child,
            View dependency) {
        float translationY = getTranslationYForSnackbar(parent, child);
        child.setTranslationY(translationY);
        return true;
    }

    private float getTranslationYForSnackbar(CoordinatorLayout parent, CircleIndicator ci) {
        float minOffset = 0;
        final List<View> dependencies = parent.getDependencies(ci);
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            if (view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(ci, view)) {
                minOffset =
                        Math.min(minOffset, ViewCompat.getTranslationY(view) - view.getHeight());
            }
        }

        return minOffset;
    }
}