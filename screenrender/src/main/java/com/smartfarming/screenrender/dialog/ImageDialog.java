package com.smartfarming.screenrender.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.manager.FileManager;
import com.smartfarming.screenrender.view.TouchImageView;

public class ImageDialog extends Dialog {

    @BindView(R2.id.ivImage)
    TouchImageView ivImage;

    @BindView(R2.id.img_close)
    ImageView imgClose;


    private final Context context;
    private String imagePath;
    private Drawable imageDrawable;

    public ImageDialog(@NonNull Context context, String imageName) {
        super(context);
        this.context = context;
        this.imagePath = imageName;
    }


    public ImageDialog(@NonNull Context context, Drawable drawable) {
        super(context);
        this.context = context;
        this.imageDrawable = drawable;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_image);

        ButterKnife.bind(this);

        if (!TextUtils.isEmpty(imagePath)) {
            //Bitmap bitmap = FileManager.getImageBitmap(context,imagePath, Constants.DIALOG);
            String path = FileManager.getImagePath(context, imagePath);
            Glide.with(context).load(path).into(ivImage);
        } else {
            ivImage.setImageDrawable(imageDrawable);
        }

//        ivImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_HEIGHT);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
