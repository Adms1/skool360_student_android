package com.anandniketanbhadaj.skool360student.Adapter;

import android.content.Context;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.ExamDatum;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.ExamModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HolidayListAdapter extends RecyclerView.Adapter<HolidayListAdapter.MyViewHolder> {

    ExamModel holidayDataResponse;

    List<MyViewHolder> myViewHoldersList;
    private Context mContext;
    private int recyclerViewHeight;

    public HolidayListAdapter(Context mContext, ExamModel holidayDataResponse, List<ExamDatum> monthwisedata, int recyclerViewHeight) {
        this.mContext = mContext;
        this.holidayDataResponse = holidayDataResponse;
        myViewHoldersList = new ArrayList<>();
        this.recyclerViewHeight = recyclerViewHeight;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holiday_listitem, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView, recyclerViewHeight);
        myViewHoldersList.add(holder);

        if (viewType > 0) {
            for (int i = 0; i < viewType; i++) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                View view = inflater.inflate(R.layout.sub_holiday_item, null);

                LinearLayout LinearData = (LinearLayout) view.findViewById(R.id.linear_click);
                LinearData.setVisibility(View.VISIBLE);

                TextView norecordtxt = (TextView) view.findViewById(R.id.norecordtxt);
                norecordtxt.setVisibility(View.GONE);

                TextView name = (TextView) view.findViewById(R.id.holiday_name_txt);
                TextView date = (TextView) view.findViewById(R.id.holiday_date_txt);
                TextView day = (TextView) view.findViewById(R.id.holiday_day);
                TextView date_txt = (TextView) view.findViewById(R.id.date_txt);

                holder.main_holiday.addView(view);
            }
        } else {
            LayoutInflater inflater = LayoutInflater.from(mContext);

            View view = inflater.inflate(R.layout.sub_holiday_item, null);

            LinearLayout LinearData = (LinearLayout) view.findViewById(R.id.linear_click);
            LinearData.setVisibility(View.GONE);


            TextView norecordtxt = (TextView) view.findViewById(R.id.norecordtxt);
            norecordtxt.setVisibility(View.VISIBLE);

            holder.main_holiday.addView(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        List<ExamDatum> examData = holidayDataResponse.getFinalArray().get(position).getData();

        holder.month_name.setText(holidayDataResponse.getFinalArray().get(position).getMonthName() + " " +
                holidayDataResponse.getFinalArray().get(position).getYear());
//        holder.month_image.setParallaxStyles(verticalMovingStyle);

        holder.translate();
        holder.month_image.setImageResource(Integer.parseInt(holidayDataResponse.getFinalArray().get(position).getMonthImage()));
//        String url = holidayDataResponse.getFinalArray().get(position).getMonthImage();
//        Log.d("url", url);
//
//        Glide.with(mContext)
//                .load(url)
//                .into(holder.month_image);
        holder.itemView.setTag(holder);
        Log.d("setext", "" + holder.main_holiday.getChildCount());
        if (holder.main_holiday.getChildCount() - 1 == examData.size()) {
            if (examData.size() > 0) {
                for (int i = 0; i < examData.size(); i++) {
                    View view = holder.main_holiday.getChildAt(i + 1);
                    TextView name = (TextView) view.findViewById(R.id.holiday_name_txt);
                    name.setText(examData.get(i).getHoliday());
                    TextView date = (TextView) view.findViewById(R.id.holiday_date_txt);
                    LinearLayout event_linear = (LinearLayout) view.findViewById(R.id.event_linear);
                    LinearLayout holiday_linear = (LinearLayout) view.findViewById(R.id.holiday_linear);

                    TextView day = (TextView) view.findViewById(R.id.holiday_day);
                    TextView event_name_txt = (TextView) view.findViewById(R.id.event_name_txt);
                    event_name_txt.setText(examData.get(i).getEvent());

                    if (!examData.get(i).getHolidayDate().equalsIgnoreCase("-")) {
                        String[] dateStr = examData.get(i).getHolidayDate().split("\\-");
                        String[] datesplit = dateStr[0].split("\\/");
                        date.setText(datesplit[0]);
                        SimpleDateFormat inFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date1 = null;
                        try {
                            date1 = inFormat.parse(dateStr[0]);
                            SimpleDateFormat outFormat = new SimpleDateFormat("EE");
                            String goal = outFormat.format(date1);
                            Log.d("day", goal);
                            day.setText(goal);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }else{
                        String[] dateStr = examData.get(i).getEventDate().split("\\-");
                        String[] datesplit = dateStr[0].split("\\/");
                        date.setText(datesplit[0]);
                        SimpleDateFormat inFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date1 = null;
                        try {
                            date1 = inFormat.parse(dateStr[0]);
                            SimpleDateFormat outFormat = new SimpleDateFormat("EE");
                            String goal = outFormat.format(date1);
                            Log.d("day", goal);
                            day.setText(goal);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!examData.get(i).getEvent().equalsIgnoreCase("")) {
                        event_linear.setVisibility(View.VISIBLE);
                    } else {
                        event_linear.setVisibility(View.GONE);
                    }
                    if (!examData.get(i).getHoliday().equalsIgnoreCase("")) {
                        holiday_linear.setVisibility(View.VISIBLE);
                    } else {
                        holiday_linear.setVisibility(View.GONE);
                    }

                    if (!examData.get(i).getHolidayDate().equalsIgnoreCase("-")) {


                        String[] holidayDatesplit = examData.get(i).getHolidayDate().split("\\-");

                        if (!holidayDatesplit[0].equalsIgnoreCase(holidayDatesplit[1])) {
                            TextView date_txt = (TextView) view.findViewById(R.id.date_txt);
                            date_txt.setVisibility(View.VISIBLE);
                            String inputPattern = "dd/MM/yyyy";
                            String outputPattern1 = "dd MMM";
                            String outputPattern2 = "dd MMM";

                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern1);
                            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern2);

                            Date startdateTime = null, enddateTime = null;
                            String str = null, StartTimeStr = null, EndTimeStr = null;

                            try {
                                startdateTime = inputFormat.parse(holidayDatesplit[0]);
                                enddateTime = inputFormat.parse(holidayDatesplit[1]);
                                StartTimeStr = outputFormat.format(startdateTime);
                                EndTimeStr = outputFormat1.format(enddateTime);

                                Log.i("mini", "Converted Date Today:" + StartTimeStr + "=" + EndTimeStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            date_txt.setText(StartTimeStr + " - " + EndTimeStr);

                        } else {
                            TextView date_txt = (TextView) view.findViewById(R.id.date_txt);
                            date_txt.setVisibility(View.GONE);
                        }
                    }
                    if (!examData.get(i).getEventDate().equalsIgnoreCase("-")) {
                        String[] eventDatesplit = examData.get(i).getEventDate().split("\\-");
                        if (!eventDatesplit[0].equalsIgnoreCase(eventDatesplit[1])) {
                            TextView edate_txt = (TextView) view.findViewById(R.id.edate_txt);
                            edate_txt.setVisibility(View.VISIBLE);
                            String inputPattern = "dd/MM/yyyy";
                            String outputPattern1 = "dd MMM";
                            String outputPattern2 = "dd MMM";

                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern1);
                            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern2);

                            Date startdateTime = null, enddateTime = null;
                            String str = null, StartTimeStr = null, EndTimeStr = null;

                            try {
                                startdateTime = inputFormat.parse(eventDatesplit[0]);
                                enddateTime = inputFormat.parse(eventDatesplit[1]);
                                StartTimeStr = outputFormat.format(startdateTime);
                                EndTimeStr = outputFormat1.format(enddateTime);

                                Log.i("mini", "Converted Date Today:" + StartTimeStr + "=" + EndTimeStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            edate_txt.setText(StartTimeStr + " - " + EndTimeStr);

                        } else {
                            TextView edate_txt = (TextView) view.findViewById(R.id.edate_txt);
                            edate_txt.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return holidayDataResponse.getFinalArray().size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return holidayDataResponse.getFinalArray().get(position).getData().size();
    }

    public void reTranslate() {
        for (MyViewHolder myViewHolder : myViewHoldersList) {
            myViewHolder.translate();
        }
    }

    @Override
    public void onViewRecycled(MyViewHolder parallaxViewHolder) {
        super.onViewRecycled(parallaxViewHolder);

        parallaxViewHolder.translate();
        // this is set manually to show to the center
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout norecord_Linear, linear_click, main_holiday;
        ImageView month_image;
        TextView month_name, holiday_name_txt, holiday_date_txt;
        private int recyclerViewHeight;
        private View itemView;

        public MyViewHolder(View view, int recyclerViewHeight) {
            super(view);
            month_name = (TextView) view.findViewById(R.id.month_name);
            month_image = (ImageView) view.findViewById(R.id.month_image);
            month_image.setScaleType(ImageView.ScaleType.MATRIX);
            main_holiday = (LinearLayout) view.findViewById(R.id.main_holiday);
            this.recyclerViewHeight = recyclerViewHeight;
            this.itemView = view;

        }

        //month_image.getMeasuredHeight()
        public void translate() {
            float translate = -itemView.getY() * ((float) month_image.getMeasuredHeight() / (float) recyclerViewHeight);
            Matrix matrix = new Matrix();
            matrix.postTranslate(0, translate);

            month_image.setImageMatrix(matrix);
        }
    }

}

