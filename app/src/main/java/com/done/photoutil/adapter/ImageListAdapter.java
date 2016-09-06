package com.done.photoutil.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.done.photoutil.R;

import java.util.List;

/**
 * Created by YZD on 2016/9/5.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    List<String> imageList;
    Context mContext;
    LayoutInflater mInflater;

    public ImageListAdapter(List<String> imageList, Context mContext) {
        this.imageList = imageList;
        this.mContext = mContext;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * 使用RecyclerView，ViewHolder是可以复用的。这根使用ListView的VIewHolder复用是一样的
         * ViewHolder创建的个数好像是可见item的个数+3
         */
        Log.e("ListAdapter", "onCreateViewHolder");
        ImageViewHolder holder = new ImageViewHolder(mInflater.inflate(R.layout.image_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        /*itemViewHolder.mTextView.setText(mDatas.get(i));
         if(mOnItemClickListener != null) {
                 *//**
                   * 这里加了判断，itemViewHolder.itemView.hasOnClickListeners()
                   * 目的是减少对象的创建，如果已经为view设置了click监听事件,就不用重复设置了
                   * 不然每次调用onBindViewHolder方法，都会创建两个监听事件对象，增加了内存的开销
                   *//*
                 if(!itemViewHolder.itemView.hasOnClickListeners()) {
                         Log.e("ListAdapter", "setOnClickListener");
                         itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                              int pos = itemViewHolder.getPosition();
                              mOnItemClickListener.onItemClick(v, pos);
                          }
                  });
              itemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                      @Override
                      public boolean onLongClick(View v) {
                              int pos = itemViewHolder.getPosition();
                              mOnItemClickListener.onItemLongClick(v, pos);
                              return true;
                          }
                  });
                     }
             }*/
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_photo);
        }
    }
}
