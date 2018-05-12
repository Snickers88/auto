package com.example.snickers.auto.Adapter;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.snickers.auto.Create_note;
import com.example.snickers.auto.DB.ContactDao;
import com.example.snickers.auto.DB.ContactModel;
import com.example.snickers.auto.R;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AutoAdapter extends RecyclerView.Adapter<AutoAdapter.AutoViewHolder> {
    private View v;

    private ArrayList<ContactModel> date = new ArrayList<>();

    public void setDate(ArrayList<ContactModel> date) {
        this.date.addAll(date);
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public AutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item, parent, false);
        return new AutoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AutoViewHolder holder, int position) {
        ContactModel item = date.get(position);
        holder.textView.setText(item.getType() + "  " + item.getPrice() + "  " + item.getDistance() + "  " + item.getVolume() + "    " + item.getTogether() + "  " + item.getDate() + "");
        holder.relativelay.setOnLongClickListener(view ->{dialog(v, position); return true;} );
        holder.relativelay.setOnClickListener(view ->{dialogupdate(v, position); } );

    }

    private void dialogupdate(View v, int position) {
        Intent intentupdate = new Intent(v.getContext() , Create_note.class);
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
        builder.setTitle("FanSerial")
                .setMessage("Видалити запис ?")
                .setCancelable(false)
                .setPositiveButton("Tак",
                        (dialog, id) -> {
                            ContactDao contactDao = new ContactDao(v);
                            if (contactDao.delete(date.get(position).get_id())) {
                                date.remove(position);
                                // for (int i=0; i <date.size(); i++)
                                //   if (date.get(i).get_id()== ge)
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