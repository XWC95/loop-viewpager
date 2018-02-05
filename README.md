# android-banner


一个蝇量级的viewpager无线轮播控件并带指示器，简约而不简单。非常好用！！！
<br>
## 效果图
<img src="/gif/app.gif" width="280px"/>

## How to use
1、build.gradle

#### Step 1.依赖
Gradle 
```
compile 'com.xwc:banner:1.0.3' //最新版本

```

#### Step 2.xml

``` xml
    <com.xwc.view.Banner
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="200dp" />
```

#### Step 3.java
```java
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
```

#### Step 4.基本配置

```java
banner.setImageUrls(Arrays.asList(pictures))
      .setImageLoader(new GlideImageLoader())
      .setBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
            }
       })
       .start();

```

#### Step 5.可见时滚动
```java
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
```


## 自定义属性说明
* 也可在代码调用,比如setDelayTime，但必须是在start方法之前设置

属性名 | 说明 | 默认值
:----------- | :----------- | :-----------
delay_time         | 轮播间隔        | 4000ms
auto_play         | 是否自动轮播        | true
image_scale_type         | 同imageView的scaleType()        | 1（CENTER_CROP）
follow         | 指示器是否更随手指滑动        | true
gravity         | 设置指示器位置        | center(下方居中)
indicator_margin         | 指示器之间距离        | 8dp
circle_color         | 指示器默认颜色        | #88FFFFFF
indicator_color         | 指示器选中颜色        | #77000000

## <a href="http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=947017886@qq.com" >联系邮箱</a>
