package com.anandniketanbhadaj.skool360student.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Models.TimetableModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterTimeTable extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<TimetableModel.Timetable.TimetableData>> _listDataChild;

    public ExpandableListAdapterTimeTable(Context context, List<String> listDataHeader,
                                          HashMap<String, ArrayList<TimetableModel.Timetable.TimetableData>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public TimetableModel.Timetable.TimetableData getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

//        final String childText = (String) getChild(groupPosition, childPosition);
        LayoutInflater infalInflater = null;
        if (convertView == null) {
            infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_timetable, null);
        }
        //if (childPosition > 0) {

            TextView txtLecture, txtSubject, txtTeacher;

            txtLecture = (TextView) convertView.findViewById(R.id.txtLecture);
            txtSubject = (TextView) convertView.findViewById(R.id.txtSubject);
            txtTeacher = (TextView) convertView.findViewById(R.id.txtTeacher);

//            if (getChild(groupPosition, childPosition-1).getLecture().contains("|")) {
                String[] lectstring = getChild(groupPosition, childPosition).getLecture().split("\\s+");
                //txtLecture.setText(Html.fromHtml(lectstring[0].trim() + ":" + lectstring[1].trim()));
//            } else {
               // txtLecture.setText("Lecture "+lectstring[0].trim()+"\n"+lectstring[1]);
            txtLecture.setText("Lecture "+lectstring[0].trim());

//            }
            txtSubject.setText(getChild(groupPosition, childPosition).getSubject());
            txtTeacher.setText(getChild(groupPosition, childPosition).getTeacher());

//        } else {
//            infalInflater = (LayoutInflater) this._context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = infalInflater.inflate(R.layout.timetable_header, null);
//        }
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_timetable, null);
        }

        if (isExpanded) {
            convertView.setBackgroundResource(R.color.orange);
        } else {
            convertView.setBackgroundResource(R.color.gray);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
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