package com.anandniketanbhadaj.skool360student.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Models.Suggestion.SuggestionInboxModel;

import java.util.ArrayList;

public class OnlinePaymentAdapter extends RecyclerView.Adapter<OnlinePaymentAdapter.MyViewHolder> {
    SuggestionInboxModel paymentLedgerModels;
    private Context context;
    private ArrayList<String> rowvalue;

    public OnlinePaymentAdapter(Context mContext, SuggestionInboxModel paymentdetailsModel) {
        this.context = mContext;
        this.paymentLedgerModels = paymentdetailsModel;
    }


    @Override
    public OnlinePaymentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_paymentlist, parent, false);
        return new OnlinePaymentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OnlinePaymentAdapter.MyViewHolder holder, final int position) {

        holder.payment_id_txt.setText(paymentLedgerModels.getOnlineTransaction().get(position).getPaymentID());
        holder.date_txt.setText(paymentLedgerModels.getOnlineTransaction().get(position).getDate());
        holder.p_status_txt.setText(paymentLedgerModels.getOnlineTransaction().get(position).getTransactionStatus());
        holder.amount_txt.setText("₹ " + Math.round(Float.parseFloat(paymentLedgerModels.getOnlineTransaction().get(position).getAmount())));
        holder.s_status_txt.setText(paymentLedgerModels.getOnlineTransaction().get(position).getSchoolStatus());

    }

    @Override
    public int getItemCount() {
        return paymentLedgerModels.getOnlineTransaction().size();
    }

    public ArrayList<String> getRowValue() {
        return rowvalue;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView payment_id_txt, date_txt, p_status_txt, amount_txt, s_status_txt;


        public MyViewHolder(View itemView) {
            super(itemView);
            payment_id_txt = (TextView) itemView.findViewById(R.id.payment_id_txt);
            date_txt = (TextView) itemView.findViewById(R.id.date_txt);
            p_status_txt = (TextView) itemView.findViewById(R.id.p_status_txt);
            amount_txt = (TextView) itemView.findViewById(R.id.amount_txt);
            s_status_txt = (TextView) itemView.findViewById(R.id.s_status_txt);
        }
    }
}



