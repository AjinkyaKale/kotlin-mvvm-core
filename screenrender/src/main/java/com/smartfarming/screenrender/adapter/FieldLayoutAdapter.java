package com.smartfarming.screenrender.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.fragment.FieldLayoutFragment;
import com.smartfarming.screenrender.manager.FileManager;
import com.smartfarming.screenrender.model.FieldModel;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.OnHtmlLinkClickListener;
import com.smartfarming.screenrender.util.Utility;
import com.smartfarming.screenrender.view.CustomFontTextView;

import java.util.ArrayList;
import java.util.List;

public class FieldLayoutAdapter extends ArrayAdapter<FieldModel> implements OnHtmlLinkClickListener {

    private Context mContext;
    private List<FieldModel> fieldModels = new ArrayList<>();
    private FieldLayoutFragment fragment;

    public FieldLayoutAdapter(FragmentActivity activity, int row_field_layout, List<FieldModel> fieldModels,
                              FieldLayoutFragment fieldLayoutFragment) {
        super(activity, row_field_layout);
        this.mContext = activity;
        this.fieldModels = fieldModels;
        this.fragment = fieldLayoutFragment;
    }


    static class ViewHolder {
        CustomFontTextView tvTitle, tvDesc;
        ImageView ivField;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_field_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (CustomFontTextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvDesc = (CustomFontTextView) convertView.findViewById(R.id.tvDescription);
            viewHolder.ivField = (ImageView) convertView.findViewById(R.id.ivField);

            // store the holder with the view.
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        AppUtility.renderHtmlInATextView(viewHolder.tvTitle, fieldModels.get(position).getTitle(), this);
        AppUtility.renderHtmlInATextView(viewHolder.tvDesc, fieldModels.get(position).getDescription(), this);

        //viewHolder.ivField.setImageBitmap(FileManager.getImageBitmap(mContext,fieldModels.get(position).getIcon(), Constants.SCREEN));
        String imagePath = FileManager.getImagePath(mContext, fieldModels.get(position).getIcon());
        Glide.with(mContext).load(imagePath).into(viewHolder.ivField);

        return convertView;
    }


    @Override
    public int getCount() {
        return fieldModels.size();
    }

    /**
     * click text on hyperlink
     *
     * @param clickedText
     */
    @Override
    public void onHtmlLinkClickListener(String clickedText) {
        String screen_id = Utility.isInteger(mContext, clickedText);
        if (!TextUtils.isEmpty(screen_id)) {
            (fragment).launchFragment(screen_id);
        }
    }


}
