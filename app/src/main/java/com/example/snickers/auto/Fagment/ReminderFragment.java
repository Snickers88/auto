package com.example.snickers.auto.Fagment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.snickers.auto.Adapter.ReminderAdapter;
import com.example.snickers.auto.Create.Create_Reminder;
import com.example.snickers.auto.DB.contacts.ContactDao;
import com.example.snickers.auto.DB.contacts.ContactModel;
import com.example.snickers.auto.DB.events.EventDao;
import com.example.snickers.auto.DB.events.EventModel;
import com.example.snickers.auto.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ReminderFragment extends Fragment {
    private ReminderAdapter reminderAdapter = new ReminderAdapter();
    private ArrayList<EventModel> eventModels = new ArrayList<>();
    private View view;
    private ArrayList<ContactModel> contactModels = new ArrayList<>();
    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_reminder, container, false);
        FloatingActionButton t = view.findViewById(R.id.floatingActionButton3);
        t.setOnClickListener(t1 -> {
            Intent in = new Intent(view.getContext(), Create_Reminder.class);
            startActivity(in);
        });


        Collections.sort(eventModels, (o1, o2) -> {
            if (o1.getDate() == null || o2.getDate() == null)

                return 0;
            SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = format.parse(o1.getDate());
                date2 = format.parse(o2.getDate());

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date1.compareTo(date2);
        });
        loading();

        notification();
        return view;
    }

    void loading() {
        eventModels.clear();
        EventDao eventDao = new EventDao(view);
        eventModels.addAll(eventDao.select());
        reminderAdapter.setDate(eventModels);
        rv = view.findViewById(R.id.rvEvent);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv.setAdapter(reminderAdapter);
    }

    EventModel eventModelsAction = new EventModel();

    void notification() {
        ContactDao contactDao = new ContactDao(view);
        contactModels.addAll(contactDao.select());
        SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy");
        for (EventModel itemEvent : eventModels) {
            int distantion = 0;
            Date date1 = null;
            try {
                date1 = format.parse(itemEvent.getDate());
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
            if (itemEvent.getDistance() < distantion) {
                eventModelsAction = itemEvent;
                dialog();
            }

        }

    }

    void dialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();

        alertDialog.setTitle("Подія");

        alertDialog.setMessage("Зробіть " + eventModelsAction.getDescription());

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Виконав", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");
                String date = sdf.format(new Date(System.currentTimeMillis()));
                EventDao eventDao = new EventDao(view);
                eventDao.add(date, eventModelsAction.getDistance(), eventModelsAction.getDescription());

                eventDao.delete(eventModelsAction.get_id());
                loading();


            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "+ 200 км", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                EventDao eventDao = new EventDao(view);

                eventDao.update(eventModelsAction.get_id(), eventModelsAction.getDate(), eventModelsAction.getDistance() + 200, eventModelsAction.getDescription());
                loading();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Пізніше", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                alertDialog.cancel();

            }
        });

        alertDialog.show();

    }
}

