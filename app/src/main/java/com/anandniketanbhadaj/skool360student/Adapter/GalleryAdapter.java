package com.anandniketanbhadaj.skool360student.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Interfacess.onViewClick;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.PhotoModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    List<PhotoModel> arrayList;
    List<String> name;
    onViewClick onViewClick;
    private Context mContext;
    private ImageLoader imageLoader;
    private String displayMode;
    private ArrayList<Integer> imageposition;

    public GalleryAdapter(Context mContext, ArrayList<String> name, ArrayList<PhotoModel> arrayList, String displayMode, onViewClick onViewClick) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.onViewClick = onViewClick;
        this.displayMode = displayMode;
    }

    @Override
    public GalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=null ;
        if(displayMode.equalsIgnoreCase("normal")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_grid_item, parent, false);


        }else{
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.detail_image_item, parent, false);
        }
        return new GalleryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.MyViewHolder holder, final int position) {
        imageLoader = ImageLoader.getInstance();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .threadPriority(Thread.MAX_PRIORITY)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)// .enableLogging()
                .build();
        imageLoader.init(config.createDefault(mContext));


        imageLoader.displayImage(AppConfiguration.GALLARY_LIVE+ arrayList.get(position).getImagePath(), holder.event_image_img);
//        holder.pic_name.setText(arrayList.get(position).getTitle());
        holder.pic_name.setVisibility(View.GONE);
        holder.main_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageposition=new ArrayList<Integer>();
                imageposition.add(position);
                onViewClick.getViewClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pic_name;
        ImageView event_image_img;
        ConstraintLayout main_linear;

        public MyViewHolder(View view) {
            super(view);
            pic_name = (TextView) view.findViewById(R.id.pic_name);
            event_image_img = (ImageView) view.findViewById(R.id.event_image_img);
            main_linear = (ConstraintLayout) view.findViewById(R.id.main_linear);
        }


    }
    public ArrayList<Integer> getposition() {
        return imageposition;
    }
}


