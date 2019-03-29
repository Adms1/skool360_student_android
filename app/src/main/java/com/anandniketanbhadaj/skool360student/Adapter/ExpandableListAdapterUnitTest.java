package com.anandniketanbhadaj.skool360student.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.ExamDatum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admsandroid on 9/4/2017.
 */

public class ExpandableListAdapterUnitTest extends BaseExpandableListAdapter {

    private Context _context;
    boolean visible = true;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<ExamDatum>> _listDataChild;
    private HashMap<Integer, Integer> visibleArray = new HashMap<Integer, Integer>();

    public ExpandableListAdapterUnitTest(Context context, List<String> listDataHeader,
                                         HashMap<String, ArrayList<ExamDatum>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public ArrayList<ExamDatum> getChild(int groupPosition, int childPosititon) {
        return _listDataChild.get(_listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, final ViewGroup parent) {

        final ArrayList<ExamDatum> childData = getChild(groupPosition, childPosition);
        final LinearLayout syllabus_linear;
        final TextView subject_name_txt, syllabus_txt;

        if (convertView == null) {
            LayoutInflater infalInflater = LayoutInflater.from(_context);
            convertView = infalInflater.inflate(R.layout.list_item_unit_test, null);
        }

        subject_name_txt = (TextView) convertView.findViewById(R.id.subject_name_txt);
        syllabus_txt = (TextView) convertView.findViewById(R.id.syllabus_txt);
        syllabus_linear = (LinearLayout) convertView.findViewById(R.id.syllabus_linear);
        subject_name_txt.setText(childData.get(childPosition).getSubject());
        visibleArray.put(groupPosition, childPosition);
        Log.d("position", visibleArray.toString());
        String[] data = childData.get(childPosition).getDetail().split("\\|");
        List<String> stringList = new ArrayList<String>(Arrays.asList(data));
        if (syllabus_linear.getChildCount() > 0) {
            syllabus_linear.removeAllViews();
        }
        final TextView[] myTextViews = new TextView[stringList.size()];
        for (int i = 0; i < stringList.size(); i++) {
            final TextView rowTextView = new TextView(_context);
            rowTextView.setBackgroundResource(R.drawable.list_line_textbox);
            rowTextView.setTextSize(12);
            rowTextView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
            rowTextView.setText(stringList.get(i));
            syllabus_linear.addView(rowTextView);
            myTextViews[i] = rowTextView;
        }
        if (childData.get(childPosition).getVisible()) {
            syllabus_linear.setVisibility(View.VISIBLE);
        } else {
            syllabus_linear.setVisibility(View.GONE);
        }

        syllabus_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < childData.size(); i++) {
                    if (i == childPosition) {
                        childData.get(childPosition).setVisible(!childData.get(childPosition).getVisible());
                    } else {
                        childData.get(i).setVisible(false);
                    }
                }
                notifyDataSetChanged();
            }
        });

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
        String[] headerTemp = getGroup(groupPosition).toString().split("\\|");
        String headerTitle = headerTemp[0];
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_timetable, null);
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
}
