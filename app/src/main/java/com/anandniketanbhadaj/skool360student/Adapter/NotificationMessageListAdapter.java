package com.anandniketanbhadaj.skool360student.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import java.util.ArrayList;

/**
 * Created by admsandroid on 9/tha/2017.
 */

public class NotificationMessageListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> message = new ArrayList<>();

    // Constructor
    public NotificationMessageListAdapter(Context c, ArrayList<String> message) {
        mContext = c;
        this.message = message;
    }

    @Override
    public int getCount() {
        return message.size();
    }

    @Override
    public Object getItem(int position) {
        return message.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView discription_txt;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_notification_message, null);

            discription_txt = (TextView) convertView.findViewById(R.id.discription_txt);
            discription_txt.setText(" "+message.get(position));
        }
        return convertView;
    }
}


