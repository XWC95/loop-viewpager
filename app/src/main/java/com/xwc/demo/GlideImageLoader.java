package com.xwc.demo;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xwc.view.ImageLoader;

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
    }
}