package com.anandniketanbhadaj.skool360student.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Models.ResultModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterResult extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<ResultModel.Data>> _listDataChild;

    public ExpandableListAdapterResult(Context context, List<String> listDataHeader,
                                       HashMap<String, ArrayList<ResultModel.Data>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public ArrayList<ResultModel.Data> getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ArrayList<ResultModel.Data> childData = getChild(groupPosition, 0);

        TextView txtDateHeader, txtSubjectHeader, txtMarksGainedHeader, txtTotalMarksHeader, txtDate, txtSubject, txtMarksGained,
                txtMarksTotal, txtTotalMarksGained, txtTotalMarks, txtInvisibleView, txtPercentage;
        LinearLayout llBottomRow;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_result, null);
        }

        txtSubjectHeader = (TextView) convertView.findViewById(R.id.txtSubjectHeader);
        txtMarksGainedHeader = (TextView) convertView.findViewById(R.id.txtMarksGainedHeader);
        txtTotalMarksHeader = (TextView) convertView.findViewById(R.id.txtTotalMarksHeader);
        txtSubject = (TextView) convertView.findViewById(R.id.txtSubject);
        txtMarksGained = (TextView) convertView.findViewById(R.id.txtMarksGained);
        txtMarksTotal = (TextView) convertView.findViewById(R.id.txtMarksTotal);
        txtTotalMarksGained = (TextView) convertView.findViewById(R.id.txtTotalMarksGained);
        txtTotalMarks = (TextView) convertView.findViewById(R.id.txtTotalMarks);
        txtInvisibleView = (TextView) convertView.findViewById(R.id.txtInvisibleView);
        txtPercentage = (TextView) convertView.findViewById(R.id.txtPercentage);
        txtDate = (TextView) convertView.findViewById(R.id.txtDate);
        txtDateHeader = (TextView) convertView.findViewById(R.id.txtDateHeader);
        llBottomRow=(LinearLayout)convertView.findViewById(R.id.llBottomRow);

        if (childPosition == 0) {
            txtSubjectHeader.setVisibility(View.VISIBLE);
            txtMarksGainedHeader.setVisibility(View.VISIBLE);
            txtTotalMarksHeader.setVisibility(View.VISIBLE);
            txtDateHeader.setVisibility(View.VISIBLE);
        } else {
            txtSubjectHeader.setVisibility(View.GONE);
            txtMarksGainedHeader.setVisibility(View.GONE);
            txtTotalMarksHeader.setVisibility(View.GONE);
            txtDateHeader.setVisibility(View.GONE);
        }

        txtSubject.setText(childData.get(childPosition).getSubjectName());
        txtMarksGained.setText(childData.get(childPosition).getMarkGained());
        txtMarksTotal.setText(childData.get(childPosition).getTestMark());
        txtDate.setText(childData.get(childPosition).getDate());

        if (childPosition == childData.size() - 1) {
//            txtInvisibleView.setVisibility(View.INVISIBLE);
//            txtTotalMarksGained.setVisibility(View.VISIBLE);
//            txtTotalMarks.setVisibility(View.VISIBLE);
//            txtPercentage.setVisibility(View.GONE);
            llBottomRow.setVisibility(View.VISIBLE);
            String[] headerTemp = getGroup(groupPosition).toString().split("\\|");
            txtTotalMarksGained.setText(headerTemp[1]);
            txtTotalMarks.setText(headerTemp[2]);
            txtPercentage.setText("Percentage : " + headerTemp[3]);
        } else {
//            txtInvisibleView.setVisibility(View.GONE);
//            txtTotalMarksGained.setVisibility(View.GONE);
//            txtTotalMarks.setVisibility(View.GONE);
//            txtPercentage.setVisibility(View.GONE);
            llBottomRow.setVisibility(View.GONE);
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