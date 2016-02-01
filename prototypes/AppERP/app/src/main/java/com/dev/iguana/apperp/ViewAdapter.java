package com.dev.iguana.apperp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by imartinez on 16/08/2015.
 */
public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.customViewHolder>{
    private LayoutInflater mLayoutInflater;
    List<Information> mData = Collections.emptyList();
    private Context context;
    public  ViewAdapter(Context context,List<Information> data){
        this.context = context;
        mLayoutInflater=LayoutInflater.from(context);
        this.mData=data;
        Log.d("constructor mData: ", "" + mData.size());
    }


    @Override
    public customViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= mLayoutInflater.inflate(R.layout.drawer_row,viewGroup,false);
        customViewHolder holder = new customViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(customViewHolder Holder, int i) {
        Holder.title.setText(mData.get(i).title);
        Holder.icons.setImageResource(mData.get(i).iconId);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class customViewHolder extends  RecyclerView.ViewHolder  {

        TextView title;
        ImageView icons;
        public customViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            title = (TextView)itemView.findViewById(R.id.listText);
            icons = (ImageView)itemView.findViewById(R.id.listIcon);
        }



    }
}
