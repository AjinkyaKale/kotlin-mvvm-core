package com.smartfarming.screenrender.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.dialog.ImageDialog;
import com.smartfarming.screenrender.manager.FileManager;
import com.smartfarming.screenrender.model.ImageModel;
import com.smartfarming.screenrender.view.CustomFontTextView;

import java.util.List;

public class ImagePestPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    Drawable[] mDrawable;

    List<ImageModel> imageModelList;

    /**
     * parametrized constructor
     *
     * @param context
     * @param imageModels
     */
    public ImagePestPagerAdapter(Context context, List<ImageModel> imageModels) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.imageModelList = imageModels;
    }

    /**
     * return array length
     *
     * @return
     */
    @Override
    public int getCount() {
        return imageModelList.size();
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
        CustomFontTextView imagName = (CustomFontTextView) itemView.findViewById(R.id.tvPestname);

        // Bitmap bitmap = FileManager.getImageBitmap(mContext,imageModelList.get(position).getName(), Constants.SCREEN);
        //Drawable drawable = FileManager.getImageDrawable(mContext , imageModelList.get(position).getName());
        String imagePath = FileManager.getImagePath(mContext, imageModelList.get(position).getName());
        Glide.with(mContext).load(imagePath).into(imageView);
        //imageView.setImageBitmap(bitmap);

        if (!TextUtils.isEmpty(imageModelList.get(position).getInfo())) {
            imagName.setText(imageModelList.get(position).getInfo());
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDialog imageDialog = new ImageDialog(mContext, imageModelList.get(position).getName());
                imageDialog.show();
            }
        });


        container.addView(itemView);

        return itemView;
    }

    /**
     * destroy created item
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
