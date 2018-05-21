package com.example.snickers.auto.Adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.snickers.auto.Create.Create_Reminder;
import com.example.snickers.auto.DB.events.EventDao;
import com.example.snickers.auto.DB.events.EventModel;
import com.example.snickers.auto.R;

import java.util.ArrayList;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.AutoViewHolder> {
    private View v;

    private ArrayList<EventModel> date = new ArrayList<>();

    public void setDate(ArrayList<EventModel> date) {
        this.date = new ArrayList<>();
        this.date.addAll(date);
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public AutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item, parent, false);
        return new ReminderAdapter.AutoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AutoViewHolder holder, int position) {
        EventModel item = date.get(position);
        holder.textView.setText(item.getDescription()+"  " + item.getDistance() + "  " +item.getDate() + "");
        holder.relativelay.setOnLongClickListener(view ->{dialog(v, position); return true;} );
        holder.relativelay.setOnClickListener(view ->{dialogupdate(v, position); } );
    }


    private void dialogupdate(View v, int position) {
        Intent intentupdate = new Intent(v.getContext() , Create_Reminder.class);
        intentupdate.putExtra("item",date.get(position));
        v.getContext().
                startActivity(intentupdate);
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class AutoViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RelativeLayout relativelay;

        public AutoViewHolder(View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            relativelay = itemView.findViewById(R.id.relativelay);
        }
    }

    void dialog(View v, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Delete")
                .setMessage("Видалити запис ?")
                .setCancelable(false)
                .setPositiveButton("Tак",
                        (dialog, id) -> {
                            EventDao eventDao = new EventDao(v);
                            if (eventDao.delete(date.get(position).get_id())) {
                                date.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                .setNegativeButton("Ні",
                        (dialog, id) -> {
                            dialog.cancel();
                        });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
