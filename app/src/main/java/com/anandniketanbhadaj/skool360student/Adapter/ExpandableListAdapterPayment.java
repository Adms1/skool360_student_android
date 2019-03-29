package com.anandniketanbhadaj.skool360student.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Models.PaymentLedgerModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admsandroid on 9/6/2017.
 */

public class ExpandableListAdapterPayment extends BaseExpandableListAdapter {

    private Context _context;
    boolean visible = true;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<PaymentLedgerModel.Data>> _listDataChildPayment;

    public ExpandableListAdapterPayment(Context context, List<String> listDataHeader,
                                        HashMap<String, ArrayList<PaymentLedgerModel.Data>> listDataChildPayment) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChildPayment = listDataChildPayment;
    }

    @Override
    public ArrayList<PaymentLedgerModel.Data> getChild(int groupPosition, int childPosititon) {
        return this._listDataChildPayment.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ArrayList<PaymentLedgerModel.Data> childData = getChild(groupPosition, 0);
        final TextView receipe_no_value_txt, mode_of_payment_value_txt, tution_fee_value_txt, transport_fee_value_txt,
                imprest_value_txt, late_fees_value_txt, waive_off_value_txt, previous_outstanding_value_txt,
                total_paid_fee_value_txt, current_outstanding_value_txt, bank_name_value_txt, bank_name_txt,
                term_value_txt, cheque_no_txt, cheque_no_value_txt;
        final LinearLayout llHeaderRow1_cheque, llHeaderRow1_bankname;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_payment, null);
        }
        receipe_no_value_txt = (TextView) convertView.findViewById(R.id.receipe_no_value_txt);
        mode_of_payment_value_txt = (TextView) convertView.findViewById(R.id.mode_of_payment_value_txt);
        tution_fee_value_txt = (TextView) convertView.findViewById(R.id.tution_fee_value_txt);
        transport_fee_value_txt = (TextView) convertView.findViewById(R.id.transport_fee_value_txt);
        imprest_value_txt = (TextView) convertView.findViewById(R.id.imprest_value_txt);
        late_fees_value_txt = (TextView) convertView.findViewById(R.id.late_fees_value_txt);
        waive_off_value_txt = (TextView) convertView.findViewById(R.id.waive_off_value_txt);
        previous_outstanding_value_txt = (TextView) convertView.findViewById(R.id.previous_outstanding_value_txt);
        total_paid_fee_value_txt = (TextView) convertView.findViewById(R.id.total_paid_fee_value_txt);
        current_outstanding_value_txt = (TextView) convertView.findViewById(R.id.current_outstanding_value_txt);
        bank_name_value_txt = (TextView) convertView.findViewById(R.id.bank_name_value_txt);
        bank_name_txt = (TextView) convertView.findViewById(R.id.bank_name_txt);
        term_value_txt = (TextView) convertView.findViewById(R.id.term_value_txt);
        cheque_no_txt = (TextView) convertView.findViewById(R.id.cheque_no_txt);
        cheque_no_value_txt = (TextView) convertView.findViewById(R.id.cheque_no_value_txt);

        llHeaderRow1_cheque = (LinearLayout) convertView.findViewById(R.id.llHeaderRow1_cheque);
        llHeaderRow1_bankname = (LinearLayout) convertView.findViewById(R.id.llHeaderRow1_bankname);


        term_value_txt.setText(childData.get(childPosition).getTermDetail());
        receipe_no_value_txt.setText(childData.get(childPosition).getReceiptNo());
        mode_of_payment_value_txt.setText(childData.get(childPosition).getPayMode());
        tution_fee_value_txt.setText("₹" + "" + childData.get(childPosition).getTuitionFee());
        transport_fee_value_txt.setText("₹" + "" + childData.get(childPosition).getTransport());
        imprest_value_txt.setText("₹" + "" + childData.get(childPosition).getImprestFee());
        late_fees_value_txt.setText("₹" + "" + childData.get(childPosition).getLatesFee());
        waive_off_value_txt.setText("₹" + "" + childData.get(childPosition).getDiscountFee());
        previous_outstanding_value_txt.setText("₹" + "" + childData.get(childPosition).getPreviousFees());
        total_paid_fee_value_txt.setText("₹" + "" + childData.get(childPosition).getPaidFee());
        current_outstanding_value_txt.setText("₹" + "" + childData.get(childPosition).getCurrentOutstandingFees());

        if (childData.get(childPosition).getPayMode().equalsIgnoreCase("Cash")) {
            llHeaderRow1_cheque.setVisibility(View.GONE);
            llHeaderRow1_bankname.setVisibility(View.GONE);
        } else if (childData.get(childPosition).getPayMode().equalsIgnoreCase("Cheque")) {
            llHeaderRow1_cheque.setVisibility(View.VISIBLE);
            llHeaderRow1_bankname.setVisibility(View.VISIBLE);
            bank_name_value_txt.setText(childData.get(childPosition).getBankName());
            cheque_no_value_txt.setText(childData.get(childPosition).getChequeNumber());
        } else if (childData.get(childPosition).getPayMode().equalsIgnoreCase("Bankslip")) {
            llHeaderRow1_bankname.setVisibility(View.VISIBLE);
            llHeaderRow1_cheque.setVisibility(View.GONE);
            bank_name_value_txt.setText(childData.get(childPosition).getBankName());
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChildPayment.get(this._listDataHeader.get(groupPosition))
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
        String headerTitle1 = headerTemp[1];
        Log.d("positon", "" + headerTitle + "" + headerTitle1);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_payment_header, null);
        }

        TextView lblPayDate = (TextView) convertView.findViewById(R.id.lblPayDate);
        TextView lblPaid = (TextView) convertView.findViewById(R.id.lblPaid);
        lblPayDate.setTypeface(null, Typeface.BOLD);
        lblPayDate.setText(headerTitle);
        lblPaid.setTypeface(null, Typeface.BOLD);
        lblPaid.setText(headerTitle1);
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

