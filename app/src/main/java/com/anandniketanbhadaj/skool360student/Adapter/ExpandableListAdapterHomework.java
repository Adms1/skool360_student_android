package com.anandniketanbhadaj.skool360student.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Models.HomeWorkModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admsandroid on 9/5/2017.
 */

public class ExpandableListAdapterHomework extends BaseExpandableListAdapter {

    boolean visible = true;
    String FontStyle;
    TextView subject_title_txt, homework_title_txt, work_status_txt, chapter_title_txt, lblchaptername, objective_title_txt, lblobjective, que_title_txt, lblque;
    LinearLayout chapter_linear, objective_linear, que_linear;
    Typeface typeface;
    SpannableStringBuilder homeworkSpanned;
    String homeworkStr;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<HomeWorkModel.HomeWorkData>> _listDataChild;

    public ExpandableListAdapterHomework(Context context, List<String> listDataHeader,
                                         HashMap<String, ArrayList<HomeWorkModel.HomeWorkData>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public ArrayList<HomeWorkModel.HomeWorkData> getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             final boolean isLastChild, View convertView, ViewGroup parent) {

        ArrayList<HomeWorkModel.HomeWorkData> childData = getChild(groupPosition, 0);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_home_work, null);
        }

        subject_title_txt = (TextView) convertView.findViewById(R.id.subject_title_txt);
        homework_title_txt = (TextView) convertView.findViewById(R.id.homework_title_txt);
        work_status_txt = (TextView) convertView.findViewById(R.id.work_status_txt);

//        chapter_title_txt = (TextView) convertView.findViewById(R.id.chapter_title_txt);
//        lblchaptername = (TextView) convertView.findViewById(R.id.lblchaptername);
//        objective_title_txt = (TextView) convertView.findViewById(R.id.objective_title_txt);
//        lblobjective = (TextView) convertView.findViewById(R.id.lblobjective);
//        que_title_txt = (TextView) convertView.findViewById(R.id.que_title_txt);
//        lblque = (TextView) convertView.findViewById(R.id.lblque);
//        chapter_linear = (LinearLayout) convertView.findViewById(R.id.chapter_linear);
//        objective_linear = (LinearLayout) convertView.findViewById(R.id.objective_linear);
//        que_linear = (LinearLayout) convertView.findViewById(R.id.que_linear);
//        String[] data = childText.split(":");
//        if(!childText.contains(":")){
//            title.setText("Proxy :");
//            imgRightSign.setVisibility(View.VISIBLE);
//            txtListChild.setText("");
//        }else {
//            title.setText(data[0]+" : ");
//            txtListChild.setText(data[1]);
//            imgRightSign.setVisibility(View.GONE);
//        }
        work_status_txt.setText(childData.get(childPosition).getHomeWorkStatus());
        if (childData.get(childPosition).getHomeWorkStatus().equalsIgnoreCase("Pending")) {
            work_status_txt.setTextColor(_context.getResources().getColor(R.color.pending));
        }else if(childData.get(childPosition).getHomeWorkStatus().equalsIgnoreCase("Not Done")){
            work_status_txt.setTextColor(_context.getResources().getColor(R.color.attendance_absent_old));
        }else if(childData.get(childPosition).getHomeWorkStatus().equalsIgnoreCase("Done")){
            work_status_txt.setTextColor(_context.getResources().getColor(R.color.attendance_present_new));
        }
        subject_title_txt.setText(Html.fromHtml(childData.get(childPosition).getSubject()));
        FontStyle = "";
        FontStyle = childData.get(childPosition).getFont();
        homeworkStr = childData.get(childPosition).getHomework().replaceAll("\\n", "").trim();

        if (!FontStyle.equalsIgnoreCase("-")) {
            SetLanguageHomework(FontStyle);
            setText(homeworkStr);
        } else {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/arial.ttf");
            homework_title_txt.setTypeface(typeface);
            setText(homeworkStr);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        if (isExpanded) {
            convertView.setBackgroundResource(R.color.orange);
        } else {
            convertView.setBackgroundResource(R.color.gray);
        }


        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

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

    public void SetLanguageHomework(String type) {
        switch (type) {
            case "ArivNdr POMt":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Fotns/Arvinder.ttf");
                homework_title_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-1":
                typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/Gujrati-Saral-1.ttf");
                homework_title_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-2":
                typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/G-SARAL2.TTF");
                homework_title_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-3":
                typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/G-SARAL3.TTF");
                homework_title_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-4":
                typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/G-SARAL4.TTF");
                homework_title_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-4":
                typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/H-SARAL0.TTF");
                homework_title_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-1":
                typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/h-saral1.TTF");
                homework_title_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-2":
                typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/h-saral2.TTF");
                homework_title_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-3":
                typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/h-saral3.TTF");
                homework_title_txt.setTypeface(typeface);
                break;
            case "Shivaji05":
                typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/Shivaji05.ttf");
                homework_title_txt.setTypeface(typeface);
                break;
            case "Shruti":
                typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/Shruti.ttf");
                homework_title_txt.setTypeface(typeface);
                break;
            default:
        }
    }

    private void setText(String html) {

        homeworkSpanned = (SpannableStringBuilder) Html.fromHtml(html);
        homeworkSpanned = trimSpannable(homeworkSpanned);

        homework_title_txt.setText(homeworkSpanned, TextView.BufferType.SPANNABLE);

    }

    private SpannableStringBuilder trimSpannable(SpannableStringBuilder spannable) {
        int trimStart = 0;
        int trimEnd = 0;
        String text = spannable.toString();

        while (text.length() > 0 && text.startsWith("\n")) {
            text = text.substring(1);
            trimStart += 1;
        }
        while (text.length() > 0 && text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
            trimEnd += 1;
        }
        return spannable.delete(0, trimStart).delete(spannable.length() - trimEnd, spannable.length());
    }
}

