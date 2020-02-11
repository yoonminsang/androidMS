package org.techtown.minsang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Calendar_RecyclerAdapter extends RecyclerView.Adapter<Calendar_RecyclerAdapter.CalendarViewHolder>{
    private ArrayList<CalendarData> arrayList;
    private Context context;


    public Calendar_RecyclerAdapter(ArrayList<CalendarData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calendar_recyclerview, parent, false);
        CalendarViewHolder calendarViewHolder = new CalendarViewHolder(view);
        return calendarViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, final int position) {
        final String mName = arrayList.get(position).getName();
        ArrayList<CalendarData2> arrayList2 = arrayList.get(position).getArrayList();
        holder.name.setText(mName);
        Calendar_RecyclerAdapter2 adapter = new Calendar_RecyclerAdapter2(arrayList2, context);
        holder.calendar_Recyclerview_2.setAdapter(adapter);
        holder.calendar_Recyclerview_2.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public int getItemCount() {
//        return arrayList.size();
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected RecyclerView calendar_Recyclerview_2;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.tx_cal_name);
            this.calendar_Recyclerview_2 = itemView.findViewById(R.id.calendar_RecyclerView_2);
        }
    }
}

