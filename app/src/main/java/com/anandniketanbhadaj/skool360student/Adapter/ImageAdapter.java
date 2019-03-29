package com.anandniketanbhadaj.skool360student.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.bumptech.glide.Glide;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public String[] mThumbIds = {
            AppConfiguration.IMAGE_LIVE+"Announcement.png",
            AppConfiguration.IMAGE_LIVE+"Attendance.png",
            AppConfiguration.IMAGE_LIVE+"Home Work.png",
            AppConfiguration.IMAGE_LIVE+"Class Work.png",
            AppConfiguration.IMAGE_LIVE+"Time Table.png",
            AppConfiguration.IMAGE_LIVE+"Exam Syllabus.png",
            AppConfiguration.IMAGE_LIVE+"Results.png",
            AppConfiguration.IMAGE_LIVE+"Report Card.png",
            AppConfiguration.IMAGE_LIVE+"Fees.png",
//            AppConfiguration.IMAGE_LIVE+"Imprest.png",
            AppConfiguration.IMAGE_LIVE+"Planner.png",
            AppConfiguration.IMAGE_LIVE+"Leave Application.png",
            AppConfiguration.IMAGE_LIVE+"Circular.png",
            AppConfiguration.IMAGE_LIVE+"Gallery.png",
            AppConfiguration.IMAGE_LIVE+"Suggestion.png",



//            R.drawable.attendance, R.drawable.home_work, R.drawable.class_work,
//            R.drawable.timetable, R.drawable.unit_test, R.drawable.results,
//            R.drawable.report_card,R.drawable.fees_1, R.drawable.imprest,
//            R.drawable.holiday, R.drawable.leave,R.drawable.circular,
//            R.drawable.gallery,R.drawable.suggestion
    };

//    public String[] mThumbNames = {"Attendance",  "Homework", "Classwork", "Timetable", "Unit Test", "Results", "Report Card",
//            "Fees", "Imprest", "Canteen", "PTM", "Message","Circular"};

    public String[] mThumbNames = {"Announcement","Attendance",  "Home Work", "Class Work", "Time Table", "Exam Syllabus", "Results", "Report Card",
            "Fees","Planner","Leave Application","Circular","Gallery","Suggestion"};
//                    "Imprest",
    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgGridOptions = null;
        TextView txtGridOptionsName = null;

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.gridview_cell, null);

        imgGridOptions = (ImageView) convertView.findViewById(R.id.imgGridOptions);
        txtGridOptionsName = (TextView) convertView.findViewById(R.id.txtGridOptionsName);

        String url = mThumbIds[position];
//        Log.d("url", url);

        Glide.with(mContext)
                .load(url)
                .into(imgGridOptions);
        txtGridOptionsName.setText(mThumbNames[position]);
        return convertView;

    }

}