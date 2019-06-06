package com.smartfarming.screenrender.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.fragment.SubStagesStageWiseGapFragment;
import com.smartfarming.screenrender.model.TextModel;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.OnHtmlLinkClickListener;
import com.smartfarming.screenrender.util.Utility;

import java.util.List;


public class SubStagesAdapter extends BaseAdapter implements OnHtmlLinkClickListener {

    private Context context;
    private List<TextModel> list;
    private SubStagesStageWiseGapFragment fragment;
    private boolean onHtmlClick = false;

    public SubStagesAdapter(Context context, List<TextModel> list, SubStagesStageWiseGapFragment subStagesStageWiseGapFragment) {
        this.context = context;
        this.list = list;
        this.fragment = subStagesStageWiseGapFragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.list_item_action_advice, null);
            holder.textViewActionAdvice = (TextView) convertView.findViewById(R.id.textView_action_advice);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        //holder.textViewActionAdvice.setText(list.get(position).getValue());
        AppUtility.renderHtmlInATextView(holder.textViewActionAdvice, list.get(position).getValue(), this);

        holder.textViewActionAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!onHtmlClick) {
                    String screen_id = list.get(position).getLinkTo();
                    if (!TextUtils.isEmpty(screen_id)) {
                        fragment.launchFragment(screen_id);
                    }
                }
            }
        });


        return convertView;
    }


    class Holder {
        TextView textViewActionAdvice;
    }


    /**
     * click text on hyperlink
     *
     * @param clickedText
     */
    @Override
    public void onHtmlLinkClickListener(String clickedText) {
        String screen_id = Utility.isInteger(context, clickedText);
        onHtmlClick = true;
        if (!TextUtils.isEmpty(screen_id)) {
            fragment.launchFragment(screen_id);
        }
    }


}
