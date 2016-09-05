package com.done.photoutil.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by YZD on 2016/9/5.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    List<String> imageList;
    Context mContext;

    public ImageListAdapter(List<String> imageList, Context mContext) {
        this.imageList = imageList;
        this.mContext = mContext;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{

        public ImageViewHolder(View itemView) {
            super(itemView);
        }
    }
}
