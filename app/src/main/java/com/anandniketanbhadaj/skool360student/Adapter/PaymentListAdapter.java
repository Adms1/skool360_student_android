package com.anandniketanbhadaj.skool360student.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.Interfacess.onViewClick;
import com.anandniketanbhadaj.skool360student.Models.Suggestion.SuggestionInboxModel;
import com.anandniketanbhadaj.skool360student.R;

import java.util.ArrayList;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.MyViewHolder> {
    private Context context;
    SuggestionInboxModel paymentLedgerModels;
    private onViewClick onViewClick;
    private ArrayList<String> rowvalue;

    public PaymentListAdapter(Context mContext,SuggestionInboxModel paymentdetailsModel, onViewClick onViewClick) {
        this.context = mContext;
        this.paymentLedgerModels = paymentdetailsModel;
        this.onViewClick = onViewClick;
    }


    @Override
    public PaymentListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_list, parent, false);
        return new PaymentListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PaymentListAdapter.MyViewHolder holder, final int position) {
        String sr = String.valueOf(position + 1);
       // holder.index_txt.setText(sr);
        holder.payment_type_txt.setText(paymentLedgerModels.getFinalArray().get(position).getPaymentType());
        holder.date_txt.setText(paymentLedgerModels.getFinalArray().get(position).getDate());
        holder.term_txt.setText(paymentLedgerModels.getFinalArray().get(position).getTerm());
        holder.amount_txt.setText("₹ " + Math.round(Float.parseFloat(String.valueOf(paymentLedgerModels.getFinalArray().get(position).getAmount()))));

        holder.view_txt.setTextColor(context.getResources().getColor(R.color.light_blue));

        holder.view_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowvalue = new ArrayList<>();
                rowvalue.add(paymentLedgerModels.getFinalArray().get(position).getURL());
                onViewClick.getViewClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentLedgerModels.getFinalArray().size();
    }

    public ArrayList<String> getRowValue() {
        return rowvalue;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, payment_type_txt, date_txt, term_txt, amount_txt, view_txt;


        public MyViewHolder(View itemView) {
            super(itemView);
          //  index_txt = (TextView) itemView.findViewById(R.id.index_txt);
            payment_type_txt = itemView.findViewById(R.id.payment_type_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            term_txt = itemView.findViewById(R.id.term_txt);
            amount_txt = itemView.findViewById(R.id.amount_txt);
            view_txt = itemView.findViewById(R.id.view_txt);
        }
    }
}


