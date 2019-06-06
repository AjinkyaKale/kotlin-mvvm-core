package com.smartfarming.screenrender.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.dialog.ImageDialog;

public class ImageDescriptionPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    Drawable[] mDrawable;

    /**
     * parametrized constructor
     *
     * @param context
     * @param drawable
     */
    public ImageDescriptionPagerAdapter(Context context, Drawable[] drawable) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mDrawable = drawable;
    }

    /**
     * return array length
     *
     * @return
     */
    @Override
    public int getCount() {
        return mDrawable.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * instantiate item to show on pager
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.viewpager_item_image, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_viewpager_item);

        imageView.setImageDrawable(mDrawable[position]);

        //imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

        container.addView(itemView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDialog imageDialog = new ImageDialog(mContext, mDrawable[position]);
                imageDialog.show();
            }
        });


        return itemView;
    }

    /**
     * destory created item
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
