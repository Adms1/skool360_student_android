package com.anandniketanbhadaj.skool360student.Activities;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {


    public RecyclerViewItemDecoration() {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = 10;
        outRect.right = 10;
        outRect.bottom = 10;
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = 10;
        } else {
            outRect.top = 0;
        }
    }
}