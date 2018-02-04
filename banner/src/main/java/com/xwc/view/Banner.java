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
import android.widget.RelativeLayout;



import java.util.ArrayList;
import java.util.List;

public class Banner extends FrameLayout implements ViewPager.OnPageChangeListener {

    public String tag = "x_banner";

    LoopViewPager viewPager;
    PageIndicator indicator;
    CircleNavigator circleNavigator;

    private Context context;
    private List imageUrls;
    private List<View> imageViews;
    private Handler handler;
    private int count;   //数据size
    private int currentItem;
    private ImageLoaderInterface imageLoader;
    private OnBannerListener bannerListener;


    private int circleColor = Color.parseColor("#88ffffff");
    private int indicatorColor = Color.parseColor("#77000000");
    private boolean isAutoPlay = true;
    private int delayTime = 4000;
    private int scaleType = 1; //imageView scaleType
    private boolean follow;
    private int gravity;  //默认居中对齐
    private int indicatorMargin;

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
        scaleType = typedArray.getInt(R.styleable.banner_image_scale_type, scaleType);
        follow = typedArray.getBoolean(R.styleable.banner_follow,follow);
        gravity = typedArray.getInt(R.styleable.banner_gravity,gravity);
        indicatorMargin = typedArray.getDimensionPixelSize(R.styleable.banner_indicator_margin,indicatorMargin);
        circleColor = typedArray.getColor(R.styleable.banner_circle_color,circleColor);
        indicatorColor = typedArray.getColor(R.styleable.banner_indicator_color,indicatorColor);
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


    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public int getIndicatorColor() {
        return indicatorColor;
    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
    }

    public void setIndicatorMargin(int indicatorMargin) {
        this.indicatorMargin = indicatorMargin;
    }

    public int getIndicatorMargin() {
        return indicatorMargin;
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
        setGravity(gravity);
        startAutoPlay();
    }

    public void setGravity(int gravity){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2,UIUtil.dip2px(getContext(),30));
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
        int margin = UIUtil.dip2px(getContext(), 16);
            switch (gravity){
                case 1:
                    params.setMargins(margin,0,0,0);
                    indicator.setLayoutParams(params);
                    break;
                case 2:
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, -1);
                    params.setMargins(0,0,margin,0);
                    indicator.setLayoutParams(params);
                    break;
            }
    }


    private void setData() {
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(new BannerPagerAdapter(imageViews, bannerListener));
        viewPager.setCurrentItem(1);
        circleNavigator = new CircleNavigator(this);
        circleNavigator.setFollowTouch(follow);
        circleNavigator.setCircleCount(imageUrls.size());
        indicator.setNavigator(circleNavigator);
        setScrollable();
    }


    public void setScrollable(){
        if ( count > 1) {
            viewPager.setScrollable(true);
        } else {
            viewPager.setScrollable(false);
        }
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
                Log.i(tag, "curr:" + Banner.this.currentItem + " count:" + count );
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
            setScaleType(imageView);
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

    private void setScaleType(View imageView) {
        if (imageView instanceof ImageView) {
            ImageView view = ((ImageView) imageView);
            switch (scaleType) {
                case 0:
                    view.setScaleType(ImageView.ScaleType.CENTER);
                    break;
                case 1:
                    view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    break;
                case 2:
                    view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    break;
                case 3:
                    view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;
                case 4:
                    view.setScaleType(ImageView.ScaleType.FIT_END);
                    break;
                case 5:
                    view.setScaleType(ImageView.ScaleType.FIT_START);
                    break;
                case 6:
                    view.setScaleType(ImageView.ScaleType.FIT_XY);
                    break;
                case 7:
                    view.setScaleType(ImageView.ScaleType.MATRIX);
                    break;
            }

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
        indicator.onPageScrolled(position-1 , positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        indicator.onPageSelected(position - 1);
    }

}
