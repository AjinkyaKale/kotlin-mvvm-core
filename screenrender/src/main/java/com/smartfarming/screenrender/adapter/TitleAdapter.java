package com.smartfarming.screenrender.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.smartfarming.screenrender.R;

import java.util.List;

public class TitleAdapter extends BaseAdapter {

    private Context context;
    private List<String> listOfPractices;

    /**
     * parametrized constructor
     *
     * @param context
     */
    public TitleAdapter(Context context, List<String> list) {
        this.context = context;
        this.listOfPractices = list;
    }

    /**
     * returns the count
     *
     * @return
     */
    @Override
    public int getCount() {
        return listOfPractices.size();
    }

    /**
     * get item
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * inflate and retuns the view
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.list_item_action_advice, null);
            holder.textViewActionAdvice = (TextView) convertView.findViewById(R.id.textView_action_advice);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }


        holder.textViewActionAdvice.setText(listOfPractices.get(position));
        return convertView;
    }

    class Holder {
        TextView textViewActionAdvice;
    }

}
