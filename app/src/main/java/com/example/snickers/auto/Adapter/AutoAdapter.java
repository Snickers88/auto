package com.example.snickers.auto.Adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.snickers.auto.Create.Create_note;
import com.example.snickers.auto.DB.contacts.ContactDao;
import com.example.snickers.auto.DB.contacts.ContactModel;
import com.example.snickers.auto.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

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
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        ContactModel item = date.get(position);

        holder.tvdatenote.setText(item.getDate()+"");
        holder.tvdistancenote.setText(item.getDistance()+"");
        holder.tvvolumenote.setText(item.getVolume()+"");
        holder.tvpricenote.setText(item.getPrice()+"");
        holder.tvtogethernote.setText(decimalFormat.format(new BigDecimal(item.getTogether()))+"");
        holder.tvtypenote.setText(item.getType()+"");

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
        TextView tvdatenote, tvdistancenote,tvtypenote,tvtogethernote,tvpricenote,tvvolumenote;
        ConstraintLayout relativelay;

        public AutoViewHolder(View itemView) {

            super(itemView);
            tvdatenote = itemView.findViewById(R.id.tvdatenote);
            tvdistancenote = itemView.findViewById(R.id.tvdistancenote);
            tvtypenote = itemView.findViewById(R.id.tvtypenote);
            tvtogethernote = itemView.findViewById(R.id.tvtogethernote);
            tvpricenote = itemView.findViewById(R.id.tvpricenote);
            tvvolumenote = itemView.findViewById(R.id.tvvolumenote);
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