package org.techtown.minsang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Calendar_RecyclerAdapter2 extends RecyclerView.Adapter<Calendar_RecyclerAdapter2.CalendarViewHolder2> {
    private ArrayList<CalendarData2> arrayList2;
    private Context context;

    public Calendar_RecyclerAdapter2(ArrayList<CalendarData2> arrayList2, Context context) {
        this.arrayList2 = arrayList2;
        this.context = context;
    }

    @NonNull
    @Override
    public CalendarViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calendar_recyclerview2, parent, false);
        CalendarViewHolder2 calendarViewHolder2 = new CalendarViewHolder2(view);
        return calendarViewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder2 holder, int position) {
        CalendarData2 calendarData2 = arrayList2.get(position);
        holder.sets.setText(calendarData2.getSets());
        holder.weight.setText(calendarData2.getWeight());
        holder.raps.setText(calendarData2.getRaps());
    }


    @Override
    public int getItemCount() {
//        return arrayList.size();
        return (null != arrayList2 ? arrayList2.size() : 0);
    }

    public class CalendarViewHolder2 extends RecyclerView.ViewHolder {
        protected TextView sets;
        protected TextView weight;
        protected TextView raps;

        public CalendarViewHolder2(@NonNull View itemView) {
            super(itemView);
            this.sets = itemView.findViewById(R.id.tx_log_sets);
            this.weight = itemView.findViewById(R.id.tx_log_weight);
            this.raps = itemView.findViewById(R.id.tx_log_raps);
        }
    }
}