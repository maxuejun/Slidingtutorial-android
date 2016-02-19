package com.example.maxuejun.firstapptest.fragment;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.PresentationPagerFragment;

/**
 * Created by Administrator on 2016/2/18.
 */
public class CustomPresentationPagerFragment extends PresentationPagerFragment {

    @Override
    protected int getPageColor(int position) {
        if (position == 0)
            return ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark);
        if (position == 1)
            return ContextCompat.getColor(getContext(), android.R.color.holo_green_dark);
        if (position == 2)
            return ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark);
        return Color.TRANSPARENT;
    }

    @Override
    protected int getIndicatorResId() {
        return 0;
    }

    @Override
    protected int getViewPagerResId() {
        return 0;
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected boolean isInfiniteScrollEnabled() {
        return true;
    }

    @Override
    protected int getPagesCount() {
        return 3;
    }

    @Override
    protected PageFragment getPage(int position) {
        if (position == 0)
            return new FirstCustomPageFragment();
        if (position == 1)
            return new SecondCustomPageFragment();
        if (position == 2)
            return new ThirdCustomPageFragment();
        throw new IllegalArgumentException("Unknown position: " + position);
    }

    @Override
    protected boolean onSkipButtonClicked(View skipButton) {
        Toast.makeText(getContext(), "Skip button clicked", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected int getButtonSkipResId() {
        return 0;
    }
}
