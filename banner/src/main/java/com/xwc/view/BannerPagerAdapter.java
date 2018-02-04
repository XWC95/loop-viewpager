package com.xwc.view;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BannerPagerAdapter extends PagerAdapter {

    private List<View> imageViews;
    private OnBannerListener listener;

    public BannerPagerAdapter(List<View> imageViews, OnBannerListener listener) {
        this.imageViews = imageViews;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        container.addView(imageViews.get(position));
        View view = imageViews.get(position);
        if (listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnBannerClick(position-1);
                }
            });
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
