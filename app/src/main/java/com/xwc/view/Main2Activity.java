package com.xwc.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity {


    private String[] pictures = new String[]
            {
                    "http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg",
                    "http://img.taopic.com/uploads/allimg/140729/240450-140HZP45790.jpg",
                    "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg",
                    "http://img.zcool.cn/community/01690955496f930000019ae92f3a4e.jpg@2o.jpg",
                    "http://www.taopic.com/uploads/allimg/140421/318743-140421213T910.jpg"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Banner banner = findViewById(R.id.banner);
        banner.setImageUrls(Arrays.asList(pictures))
                .setImageLoader(new GlideImageLoader())
                .setBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Toast.makeText(Main2Activity.this, position + "", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}
