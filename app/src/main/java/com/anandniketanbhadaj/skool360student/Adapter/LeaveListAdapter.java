package com.anandniketanbhadaj.skool360student.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.ExamModel;
import com.anandniketanbhadaj.skool360student.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LeaveListAdapter extends RecyclerView.Adapter<LeaveListAdapter.MyViewHolder> {

    ExamModel arrayList;
    private Context mContext;

    public LeaveListAdapter(Context mContext, ExamModel arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public LeaveListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leave_report_list_item, parent, false);
        return new LeaveListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LeaveListAdapter.MyViewHolder holder, int position) {

        String str = String.valueOf(position + 1);
        if (arrayList.getFinalArray().get(position).getStatus().equalsIgnoreCase("Rejected")){
            holder.status_txt.setTextColor(Color.parseColor("#ed1c24"));
        }else if(arrayList.getFinalArray().get(position).getStatus().equalsIgnoreCase("Pending")){
            holder.status_txt.setTextColor(Color.parseColor("#FFD8B834"));
        }else{
            holder.status_txt.setTextColor(Color.parseColor("#86c129"));
        }
        String inputPattern = "dd/MM/yyyy";
        String outputPattern1 = "dd MMM";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern1);


        Date startdateTime = null, enddateTime = null;
        String StartTimeStr = null, EndDateTimeStr = null;

        try {
            startdateTime = inputFormat.parse(arrayList.getFinalArray().get(position).getFromDate());
            StartTimeStr = outputFormat.format(startdateTime);

            enddateTime = inputFormat.parse(arrayList.getFinalArray().get(position).getToDate());
            EndDateTimeStr = outputFormat.format(enddateTime);

            Log.i("mini", "Converted Date Today:" + StartTimeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (arrayList.getFinalArray().get(position).getReason().length() >= 17 && arrayList.getFinalArray().get(position).getReason().length() <= 32) {
            holder.reason_txt.setText(arrayList.getFinalArray().get(position).getReason());
            if (StartTimeStr.equalsIgnoreCase(EndDateTimeStr)) {
                holder.start_date_txt.setText(StartTimeStr + "\n");
            } else {
                holder.start_date_txt.setText(StartTimeStr + " - " + EndDateTimeStr + "\n");
            }
            holder.srno_txt.setText(str+"\n");
            holder.status_txt.setText(arrayList.getFinalArray().get(position).getStatus()+"\n");
        } else if (arrayList.getFinalArray().get(position).getReason().length() >= 32) {
            holder.reason_txt.setText(arrayList.getFinalArray().get(position).getReason());
            if (StartTimeStr.equalsIgnoreCase(EndDateTimeStr)) {
                holder.start_date_txt.setText(StartTimeStr + "\n" + "\n");
            } else {
                holder.start_date_txt.setText(StartTimeStr + " - " + EndDateTimeStr + "\n" + "\n");
            }
            holder.srno_txt.setText(str+"\n"+"\n");
            holder.status_txt.setText(arrayList.getFinalArray().get(position).getStatus()+"\n"+"\n");
        } else {
            holder.reason_txt.setText(arrayList.getFinalArray().get(position).getReason());
            if (StartTimeStr.equalsIgnoreCase(EndDateTimeStr)) {
                holder.start_date_txt.setText(StartTimeStr);
            } else {
                holder.start_date_txt.setText(StartTimeStr + " - " + EndDateTimeStr);
            }
            holder.srno_txt.setText(str);
            holder.status_txt.setText(arrayList.getFinalArray().get(position).getStatus());
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.getFinalArray().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView srno_txt, start_date_txt, reason_txt, end_date_txt, status_txt;

        public MyViewHolder(View view) {
            super(view);
            srno_txt = view.findViewById(R.id.srno_txt);
            start_date_txt = view.findViewById(R.id.start_date_txt);
            reason_txt = view.findViewById(R.id.reason_txt);
            status_txt = view.findViewById(R.id.status_txt);
        }
    }
}

