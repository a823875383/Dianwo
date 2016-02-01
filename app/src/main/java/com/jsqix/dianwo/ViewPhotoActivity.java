package com.jsqix.dianwo;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.jsqix.dianwo.base.ToolBarActivity;
import com.jsqix.dianwo.view.HackyViewPager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import uk.co.senab.photoview.PhotoView;

@ContentView(R.layout.activity_view_photo)
public class ViewPhotoActivity extends ToolBarActivity {
    @ViewInject(R.id.view_pager)
    private HackyViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("查看图片");
        mViewPager.setAdapter(new SamplePagerAdapter());

    }

    static class SamplePagerAdapter extends PagerAdapter {

        private static final int[] sDrawables = {R.mipmap.wallpaper, R.mipmap.wallpaper, R.mipmap.wallpaper,
                R.mipmap.wallpaper, R.mipmap.wallpaper, R.mipmap.wallpaper};

        @Override
        public int getCount() {
            return sDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setImageResource(sDrawables[position]);

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
