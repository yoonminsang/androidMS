package org.techtown.minsang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class NotePad_RecyclerAdapter extends RecyclerView.Adapter<NotePad_RecyclerAdapter.NotePadViewHolder>{
    private ArrayList<NotePadData> arrayList;
    private Context context;

    public NotePad_RecyclerAdapter(ArrayList<NotePadData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotePadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notepad_recyclerview, parent, false);
        NotePadViewHolder notePadViewHolder = new NotePadViewHolder(view);
        return notePadViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotePadViewHolder holder, int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.contents.setText(arrayList.get(position).getContents());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class NotePadViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView contents;

        public NotePadViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tx_note_title);
            this.contents = itemView.findViewById(R.id.tx_note_contents);
        }
    }
}