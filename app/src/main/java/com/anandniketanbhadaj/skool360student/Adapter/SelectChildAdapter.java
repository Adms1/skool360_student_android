package com.anandniketanbhadaj.skool360student.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.SelectChildModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SelectChildAdapter extends RecyclerView.Adapter<SelectChildAdapter.MyViewHolder> {

    ArrayList<SelectChildModel.FinalArray> arrayList;
    private Context mContext;

    public SelectChildAdapter(Context mContext, ArrayList<SelectChildModel.FinalArray> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_child_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if(position % 2 != 0){

            holder.proleft.setVisibility(View.VISIBLE);
            holder.proright.setVisibility(View.GONE);
            holder.llleft.setVisibility(View.GONE);
            holder.llright.setVisibility(View.VISIBLE);

            Picasso.get().load(AppConfiguration.LIVE_BASE_URL + arrayList.get(position).getStudentimage()).placeholder(R.drawable.profile_pic_holder).into(holder.proleft);
            holder.graderight.setText(arrayList.get(position).getGradesection());
            holder.nameright.setText(arrayList.get(position).getStudentname());
        }else {

            holder.proleft.setVisibility(View.GONE);
            holder.proright.setVisibility(View.VISIBLE);
            holder.llleft.setVisibility(View.VISIBLE);
            holder.llright.setVisibility(View.GONE);

            Picasso.get().load(AppConfiguration.LIVE_BASE_URL + arrayList.get(position).getStudentimage()).placeholder(R.drawable.profile_pic_holder).into(holder.proright);
            holder.nameleft.setText(arrayList.get(position).getStudentname());
            holder.gradeleft.setText(arrayList.get(position).getGradesection());

        }

        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.setPref(mContext, "studid", arrayList.get(position).getStudentid());
                Utility.setPref(mContext, "locationId", arrayList.get(position).getLocationid());
                Utility.setPref(mContext, "studname", arrayList.get(position).getStudentname());
                Utility.setPref(mContext, "FamilyID", arrayList.get(position).getFamilyid());
                Utility.setPref(mContext, "standardID", arrayList.get(position).getStandardid());
                Utility.setPref(mContext, "ClassID", arrayList.get(position).getClassid());
                Utility.setPref(mContext, "TermID", arrayList.get(position).getTermid());//arrayList.get(position).get("TermID"));
                Utility.setPref(mContext, "RegisterStatus", arrayList.get(position).getRegisterstatus());

                Intent intent = new Intent(mContext, DashBoardActivity.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameleft, nameright, gradeleft, graderight;
        ImageView proleft, proright;
        LinearLayout llleft, llright;
        RelativeLayout rlMain;

        public MyViewHolder(View view) {
            super(view);
            nameleft = view.findViewById(R.id.select_child_name_left);
            nameright = view.findViewById(R.id.select_child_name_right);
            gradeleft = view.findViewById(R.id.select_child_grade_left);
            graderight = view.findViewById(R.id.select_child_grade_right);
            proleft = view.findViewById(R.id.select_child_list_profile_left);
            proright = view.findViewById(R.id.select_child_list_profile_right);
            llleft = view.findViewById(R.id.select_childe_detail_left);
            llright = view.findViewById(R.id.select_Child_detail_right);
            rlMain = view.findViewById(R.id.select_child_mainll);

        }
    }
}
