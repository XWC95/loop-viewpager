package com.xwc.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xwc.view.Banner;
import com.xwc.view.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Example2Fragment extends Fragment {

        private String[] pictures = new String[]
            {
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518339822&di=2368c09375a225f08cc88c5a0a186226&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.67.com%2Fupload%2Fimages%2F2016%2F09%2F20%2FbHdqMTQ3NDM0MjE3MA%3D%3D.jpg",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518339338&di=901620072c4748a4e6f362c09456c757&imgtype=jpg&er=1&src=http%3A%2F%2Fmu1.sinaimg.cn%2Fcrop.0x230x1073x1073%2Fweiyinyue.music.sina.com.cn%2Fmovie_game%2F1450247893157.jpg",
                    "http://pic1.win4000.com/wallpaper/3/56de34a6130d1.jpg",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517745462767&di=0bc7672015075c140f8913731708c09f&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201512%2F26%2F20151226085607_FRSiP.jpeg",
            };

    private Banner banner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_example2, container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        banner = view.findViewById(R.id.banner);

        List<String> images = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518339822&di=2368c09375a225f08cc88c5a0a186226&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.67.com%2Fupload%2Fimages%2F2016%2F09%2F20%2FbHdqMTQ3NDM0MjE3MA%3D%3D.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518339338&di=901620072c4748a4e6f362c09456c757&imgtype=jpg&er=1&src=http%3A%2F%2Fmu1.sinaimg.cn%2Fcrop.0x230x1073x1073%2Fweiyinyue.music.sina.com.cn%2Fmovie_game%2F1450247893157.jpg");
        images.add("http://pic1.win4000.com/wallpaper/3/56de34a6130d1.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517745462767&di=0bc7672015075c140f8913731708c09f&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201512%2F26%2F20151226085607_FRSiP.jpeg");

        banner.setImageUrls(images)
                .setImageLoader(new GlideImageLoader())
                .setBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Toast.makeText(getActivity(), "点击了第："+position +"个", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();

    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }
}
