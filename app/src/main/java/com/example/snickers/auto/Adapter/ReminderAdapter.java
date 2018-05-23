package com.example.snickers.auto.Adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.snickers.auto.Create.Create_Reminder;
import com.example.snickers.auto.DB.contacts.ContactDao;
import com.example.snickers.auto.DB.contacts.ContactModel;
import com.example.snickers.auto.DB.events.EventDao;
import com.example.snickers.auto.DB.events.EventModel;
import com.example.snickers.auto.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
                .inflate(R.layout.item_redminder, parent, false);
        return new ReminderAdapter.AutoViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull AutoViewHolder holder, int position) {
        EventModel item = date.get(position);
        int nowDistantion=notification(item),endDistantion=item.getDistance();

        holder.tvDateReminder.setText(item.getDate());
        holder.tvDistanceReminder.setText(nowDistantion + "/" +endDistantion + "");
        holder.tvNameReminder.setText(item.getDescription());

        holder.pbReminder.setMax(endDistantion);
        holder.pbReminder.setProgress(nowDistantion);

        holder.rlReminder.setOnLongClickListener(view -> {
            dialog(v, position);
            return true;
        });
        holder.rlReminder.setOnClickListener(view -> {
            dialogupdate(v, position);
        });
    }

    int notification(EventModel item) {
        ContactDao contactDao = new ContactDao(v);
        ArrayList<ContactModel> contactModels = new ArrayList<>();


        contactModels.addAll(contactDao.select());
        SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy");
        int distantion = 0;
        Date date1 = null;
        try {
            date1 = format.parse(item.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (ContactModel itemContact : contactModels) {

            Date date2 = null;
            try {
                date2 = format.parse(itemContact.getDate());

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (date1 != null && date2 != null && date1.compareTo(date2) <= 0) {
                distantion += itemContact.getDistance();
            }


        }
        return distantion;
    }

    private void dialogupdate(View v, int position) {
        Intent intentupdate = new Intent(v.getContext(), Create_Reminder.class);
        intentupdate.putExtra("item", date.get(position));
        v.getContext().
                startActivity(intentupdate);
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class AutoViewHolder extends RecyclerView.ViewHolder {
        TextView tvDistanceReminder, tvDateReminder, tvNameReminder;
        RelativeLayout rlReminder;
        ProgressBar pbReminder;

        public AutoViewHolder(View itemView) {

            super(itemView);
            tvDistanceReminder = itemView.findViewById(R.id.tvDistanceReminder);
            tvDateReminder = itemView.findViewById(R.id.tvDateReminder);
            tvNameReminder = itemView.findViewById(R.id.tvNameReminder);
            rlReminder = itemView.findViewById(R.id.rlReminder);
            pbReminder = itemView.findViewById(R.id.pbReminder);
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
