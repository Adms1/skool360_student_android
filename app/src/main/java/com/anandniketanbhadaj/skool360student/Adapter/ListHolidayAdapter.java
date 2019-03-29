package com.anandniketanbhadaj.skool360student.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Models.AttendanceModel;

import java.util.ArrayList;


public class ListHolidayAdapter extends RecyclerView.Adapter<ListHolidayAdapter.MyViewHolder> {
    ArrayList<AttendanceModel> arrayList;
    private Context mContext;

    public ListHolidayAdapter(Context mContext, ArrayList<AttendanceModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_holiday_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.holiday_name_txt.setText(arrayList.get(0).getHolidayAtt().get(position).getHolidayName());

        String []splitDate=arrayList.get(0).getHolidayAtt().get(position).getDate().split("\\-");
        if (splitDate[0].equalsIgnoreCase(splitDate[1])){
            holder.holiday_date_txt.setText(splitDate[0]);
        }else{
            holder.holiday_date_txt.setText(splitDate[0]+" - "+splitDate[1]);
        }



        if (Integer.parseInt(arrayList.get(0).getHolidayAtt().get(position).getCount()) < 10) {
            holder.holiday_count_txt.setText("0" + arrayList.get(0).getHolidayAtt().get(position).getCount());
        } else {
            holder.holiday_count_txt.setText(arrayList.get(0).getHolidayAtt().get(position).getCount());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.get(0).getHolidayAtt().size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView holiday_name_txt, holiday_count_txt, holiday_date_txt, holiday_days_txt;
        public LinearLayout linear_click;

        public MyViewHolder(View view) {
            super(view);
            linear_click = (LinearLayout) view.findViewById(R.id.linear_click);
            holiday_days_txt = (TextView) view.findViewById(R.id.holiday_days_txt);
            holiday_date_txt = (TextView) view.findViewById(R.id.holiday_date_txt);
            holiday_count_txt = (TextView) view.findViewById(R.id.holiday_count_txt);
            holiday_name_txt = (TextView) view.findViewById(R.id.holiday_name_txt);
        }
    }
}


