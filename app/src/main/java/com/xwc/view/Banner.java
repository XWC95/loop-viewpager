package com.xwc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xwc on 2018/2/1.
 */
public class Banner extends FrameLayout implements ViewPager.OnPageChangeListener {

    public String tag = "x_banner";

    LoopViewPager viewPager;
    PageIndicator indicator;

    private Context context;
    private List imageUrls;
    private List<View> imageViews;
    private Handler handler;
    private int count;   //数据size
    private int currentItem;
    private ImageLoaderInterface imageLoader;
    private OnBannerListener bannerListener;

    private boolean isAutoPlay;
    private int delayTime = 4000;


    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        imageUrls = new ArrayList<>();
        imageViews = new ArrayList<>();

        View view = LayoutInflater.from(context).inflate(R.layout.view_banner, this, true);
        viewPager = view.findViewById(R.id.viewpager);
        indicator = view.findViewById(R.id.pager_indicator);

        handleTypeArray(context, attrs);
    }


    public void handleTypeArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.banner);
        delayTime = typedArray.getInt(R.styleable.banner_delay_time, delayTime);
        isAutoPlay = typedArray.getBoolean(R.styleable.banner_auto_play, isAutoPlay);
        handler = new Handler();
        typedArray.recycle();

    }

    public Banner setDelayTime(int delayTime) {
        this.delayTime = delayTime;
        return this;
    }

    public Banner setImageUrls(List<?> imageUrls) {
        this.imageUrls = imageUrls;
        count = imageUrls.size();
        return this;
    }

    public Banner setAutoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
        return this;
    }

    public Banner setImageLoader(ImageLoaderInterface imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    public Banner setBannerListener(OnBannerListener bannerListener) {
        this.bannerListener = bannerListener;
        return this;
    }

    public void startAutoPlay() {
        handler.removeCallbacks(task);
        handler.postDelayed(task, delayTime);
    }

    public void stopAutoPlay() {
        handler.removeCallbacks(task);
    }

    public void start() {
        setImageList(imageUrls);
        setData();
        startAutoPlay();
    }

    private void setData() {
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(new BannerPagerAdapter(imageViews, bannerListener));
        viewPager.setCurrentItem(1);
        CircleNavigator circleNavigator = new CircleNavigator(getContext());
        circleNavigator.setCircleCount(imageUrls.size());
        circleNavigator.setCircleColor(Color.RED);
        indicator.setNavigator(circleNavigator);
//        LoopViewPager.bindIndicator(indicator, viewPager);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAutoPlay) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                startAutoPlay();
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAutoPlay();
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (isAutoPlay && count > 1) {
                int currentItem = viewPager.getCurrentItem();
                Banner.this.currentItem = Banner.this.currentItem % (count + 1) + 1;
                Log.i(tag, "curr:" + Banner.this.currentItem + " count:" + count + "xwc:" + currentItem);
                if (Banner.this.currentItem == 1) {
                    viewPager.setCurrentItem(Banner.this.currentItem, false);
                    handler.post(task);
                } else {
                    viewPager.setCurrentItem(Banner.this.currentItem);
                    handler.postDelayed(task, delayTime);
                }
            }
        }
    };

    private void setImageList(List<?> imagesUrl) {
        if (imagesUrl == null || imagesUrl.size() <= 0) {
            Log.e(tag, "Please set the images data.");
            return;
        }
        for (int i = 0; i <= count + 1; i++) {
            View imageView = null;
            if (imageLoader != null) {
                imageView = imageLoader.createImageView(context);
            }
            if (imageView == null) {
                imageView = new ImageView(context);
            }
            //TODO setScaleType
//            setScaleType(imageView);
            Object url = null;
            if (i == 0) {
                url = imagesUrl.get(count - 1);
            } else if (i == count + 1) {
                url = imagesUrl.get(0);
            } else {
                url = imagesUrl.get(i - 1);
            }
            imageViews.add(imageView);
            if (imageLoader != null)
                imageLoader.displayImage(context, url, imageView);
            else
                Log.e(tag, "Please set images loader.");
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        currentItem = viewPager.getCurrentItem();
        switch (state) {
            case 0://No operation
                if (currentItem == 0) {
                    viewPager.setCurrentItem(count, false);
                } else if (currentItem == count + 1) {
                    viewPager.setCurrentItem(1, false);
                }
                break;
            case 1://start Sliding
                if (currentItem == count + 1) {
                    viewPager.setCurrentItem(1, false);
                } else if (currentItem == 0) {
                    viewPager.setCurrentItem(count, false);
                }
                break;
            case 2://end Sliding
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        indicator.onPageScrolled(position - 1, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        indicator.onPageSelected(position - 1);
    }

}
