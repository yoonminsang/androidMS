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

public class Album_RecyclerAdapter extends RecyclerView.Adapter<Album_RecyclerAdapter.AlbumViewHolder> {
    private ArrayList<AlbumData> arrayList;
    private Context context;

    public Album_RecyclerAdapter(ArrayList<AlbumData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Album_RecyclerAdapter.AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_album_recyclerview, parent, false);
        AlbumViewHolder albumViewHolder = new AlbumViewHolder(view);
        return albumViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        holder.albumimage.setImageBitmap(arrayList.get(position).getAlbumimage());
        holder.albumdate.setText(arrayList.get(position).getAlbumdate());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {

        protected ImageView albumimage;
        protected TextView albumdate;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            this.albumimage = itemView.findViewById(R.id.im_album);
            this.albumdate = itemView.findViewById(R.id.tx_album_date);
        }
    }
}