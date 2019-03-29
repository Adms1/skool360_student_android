package com.anandniketanbhadaj.skool360student.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Models.Suggestion.InboxFinalArray;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterInbox extends BaseExpandableListAdapter {

    TextView txtsuggestion, txtreply, txtsuggestion_view, txtreply_view, txt_dot, txt_dot1, txtsuggestiondate;
    String pageType, suggestiondateStr;
    int SessionHour = 0;
    Integer SessionMinit = 0;
    String SessionDurationminit = "", SessionDurationHours = "";
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<InboxFinalArray>> listChildData;

    public ExpandableListAdapterInbox(Context context, List<String> listDataHeader,
                                      HashMap<String, List<InboxFinalArray>> listChildData, String pageType) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listChildData;
        this.pageType = pageType;
        notifyDataSetChanged();
    }

    @Override
    public List<InboxFinalArray> getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        List<InboxFinalArray> childData = getChild(groupPosition, 0);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_inbox, null);
        }

        txtreply = (TextView) convertView.findViewById(R.id.txtreply);
        txtsuggestion = (TextView) convertView.findViewById(R.id.txtsuggestion);
        txtsuggestiondate = (TextView) convertView.findViewById(R.id.txtsuggestiondate);

        txtreply.setText(childData.get(childPosition).getResponse());
        txtsuggestion.setText(childData.get(childPosition).getSuggestion());


        String[] dateHours = childData.get(childPosition).getDate().split("\\t");
        String inputPattern = "yyyy-MM-dd'T'hh:mm:ss.SSS";
        String outputPattern1 = "dd MMM yyyy hh:mm:ss a";


        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern1);


        Date startdateTime = null;
        String str = null, StartTimeStr = null;

        try {
            startdateTime = inputFormat.parse(dateHours[0]);
            StartTimeStr = outputFormat.format(startdateTime);
            txtsuggestiondate.setText(StartTimeStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (pageType.equalsIgnoreCase("Inbox")) {
            txtreply.setVisibility(View.VISIBLE);
            txtreply.setTypeface(txtreply.getTypeface(), Typeface.BOLD);
            txtsuggestion.setTypeface(txtsuggestion.getTypeface(), Typeface.NORMAL);
        } else {
            txtreply.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_inbox, null);
        }
        TextView date_inbox_txt, subject_inbox_txt, line_txt, day_inbox_txt;
        date_inbox_txt = (TextView) convertView.findViewById(R.id.date_inbox_txt);
        subject_inbox_txt = (TextView) convertView.findViewById(R.id.subject_inbox_txt);
        line_txt = (TextView) convertView.findViewById(R.id.line_txt);
        day_inbox_txt = (TextView) convertView.findViewById(R.id.day_inbox_txt);
        String CurrentTime = "";


        Date currentTimeDate = Calendar.getInstance().getTime();
        CurrentTime = String.valueOf(currentTimeDate);

        String[] dateHours = headerTitle1.split("\\t");
        String inputPattern = "yyyy-MM-dd";
        String outputPattern1 = "dd MMM yyyy";
        String inputdayPattern = "dd/MM/yyyy";
        String inputtimePattern = "yyyy-MM-dd'T'hh:mm:ss.SSS";
        String outputtimePattern = "hh:mm aa";
        String inputCurrentTimePattern = "EEE MMM d HH:mm:ss zzz yyyy";


        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern1);
        SimpleDateFormat inputdayFormat = new SimpleDateFormat(inputdayPattern);
        SimpleDateFormat inputtimeFormat = new SimpleDateFormat(inputtimePattern);
        SimpleDateFormat outputtimeFormat = new SimpleDateFormat(outputtimePattern);
        SimpleDateFormat inputCurrentTimeFormat = new SimpleDateFormat(inputCurrentTimePattern);
        SimpleDateFormat outputCurrentTimeFormat = new SimpleDateFormat(outputtimePattern);


        String dateAfterString = Utility.getTodaysDate();

        Date startdateTime = null, ResponseTimeStr = null, CurrentTimeStr = null;
        String str = null, StartTimeStr = null, ConvertResponseTimeStr = null, ConvertCurrentTimeStr = null;

        try {
            startdateTime = inputFormat.parse(dateHours[0]);
            StartTimeStr = outputFormat.format(startdateTime);

            str = inputdayFormat.format(startdateTime);
            Log.i("mini", "Converted Date Today:" + StartTimeStr);

            Date dateBefore = inputdayFormat.parse(str);

            Date dateAfter = inputdayFormat.parse(dateAfterString);
            long difference = dateAfter.getTime() - dateBefore.getTime();
            String daysBetween = String.valueOf((difference / (1000 * 60 * 60 * 24)));
            date_inbox_txt.setText(StartTimeStr);

            if (daysBetween.equals("0")) {
                ResponseTimeStr = inputtimeFormat.parse(dateHours[0]);
                ConvertResponseTimeStr = outputtimeFormat.format(ResponseTimeStr);
                Log.d("Time", ConvertResponseTimeStr);
                CurrentTimeStr = inputCurrentTimeFormat.parse(CurrentTime);
                ConvertCurrentTimeStr = outputCurrentTimeFormat.format(CurrentTimeStr);
                Log.d("CurrentTimeCOnvert", ConvertCurrentTimeStr);

                calculateHours(ConvertCurrentTimeStr, ConvertResponseTimeStr);
                SessionDurationHours=SessionDurationHours.replace("-","");
                day_inbox_txt.setText(SessionDurationHours + " ago");
            } else {
                day_inbox_txt.setText(daysBetween + " days ago");
            }
            System.out.println("Number of Days between dates: " + daysBetween);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        subject_inbox_txt.setText(headerTitle2);

        if (isExpanded) {
            line_txt.setBackgroundResource(R.color.blue);
        } else {
            line_txt.setBackgroundResource(R.color.orange);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void calculateHours(String time1, String time2) {
        Date date1, date2;
        int days, hours, min;
        String hourstr, minstr;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        try {

            date2 = simpleDateFormat.parse(time2);
            date1 = simpleDateFormat.parse(time1);

            long difference = date2.getTime() - date1.getTime();
            days = (int) (difference / (1000 * 60 * 60 * 24));
            hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            hours = (hours < 0 ? -hours : hours);
            SessionHour = hours;
            SessionMinit = min;
            Log.i("======= Hours", " :: " + hours + ":" + min);

            if (SessionHour > 0) {
                SessionDurationHours = SessionHour + " hrs";
            } else {
                SessionDurationHours = SessionMinit + " minute";
            }
            Log.d("SessionTIme", SessionDurationHours);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}




