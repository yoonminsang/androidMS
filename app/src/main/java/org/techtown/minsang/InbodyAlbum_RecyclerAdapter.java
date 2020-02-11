package org.techtown.minsang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InbodyAlbum_RecyclerAdapter extends RecyclerView.Adapter<InbodyAlbum_RecyclerAdapter.InbodyAlbumViewHolder>{
    private ArrayList<InbodyAlbumData> arrayList;
    private Context context;

    public InbodyAlbum_RecyclerAdapter(ArrayList<InbodyAlbumData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public InbodyAlbum_RecyclerAdapter.InbodyAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inbodyalbum_recyclerview, parent, false);
        InbodyAlbum_RecyclerAdapter.InbodyAlbumViewHolder inbodyAlbumViewHolder = new InbodyAlbum_RecyclerAdapter.InbodyAlbumViewHolder(view);
        return inbodyAlbumViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InbodyAlbum_RecyclerAdapter.InbodyAlbumViewHolder holder, int position) {
        holder.albumimage.setImageBitmap(arrayList.get(position).getAlbumimage());
        holder.albumdate.setText(arrayList.get(position).getAlbumdate());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class InbodyAlbumViewHolder extends RecyclerView.ViewHolder {

        protected ImageView albumimage;
        protected TextView albumdate;

        public InbodyAlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            this.albumimage = itemView.findViewById(R.id.im_inbodyalbum);
            this.albumdate = itemView.findViewById(R.id.tx_inbodyalbum_date);
        }
    }
}