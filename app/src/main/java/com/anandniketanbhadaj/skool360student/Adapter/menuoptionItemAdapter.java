package com.anandniketanbhadaj.skool360student.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Models.menuoptionItem;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import java.util.ArrayList;

public class menuoptionItemAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<menuoptionItem> menuOptionItems;
	
	public menuoptionItemAdapter(Context context, ArrayList<menuoptionItem> menuOptionItems){
		this.context = context;
		this.menuOptionItems = menuOptionItems;
	}
	@Override
	public int getCount() {
		return menuOptionItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return menuOptionItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.menu_drawer_item, null);
        }
         
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

		if(position == 1){
			String[] name = (Utility.getPref(context, "studname").split(" "));
			txtTitle.setText( name[0] + " Profile");
		}else {
			txtTitle.setText(menuOptionItems.get(position).getName());
		}

        return convertView;
	}

}
