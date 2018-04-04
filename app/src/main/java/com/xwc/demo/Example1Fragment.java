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
import java.util.List;


public class Example1Fragment extends Fragment {

    private Banner banner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_example, container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        banner = view.findViewById(R.id.banner);

        List<Integer> pictures = new ArrayList<>();
        pictures.add(R.mipmap.page1);
        pictures.add(R.mipmap.page2);
        pictures.add(R.mipmap.page3);
        pictures.add(R.mipmap.page4);

        banner.setImageUrls(pictures)
                .setImageLoader(new GlideImageLoader())
                .setBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Toast.makeText(getActivity(), "点击了第："+position +"个", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();

        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"OK",Toast.LENGTH_SHORT).show();

            }
        });
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
