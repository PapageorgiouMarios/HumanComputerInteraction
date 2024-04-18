package com.example.humancomputerinteraction;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;

public class ReminderAdapter extends BaseAdapter
{
    private final Context context;
    private final ArrayList<String> json_reminders;
    private final ArrayList<Boolean> switchStates; // Check if alarm is on or off

    public ReminderAdapter(Context context, ArrayList<String> json_reminders)
    {
        this.context = context;
        this.json_reminders = json_reminders;

        this.switchStates = new ArrayList<>(); // initialize the ArrayList

        if(json_reminders.size() > 0)
        {
            for (int i = 0; i < json_reminders.size(); i++)
            {
                switchStates.add(false); // initialize all Switch states to false
            }
        }
    }

    private OnDeleteListener onDeleteListener;

    public void setOnDeleteListener(OnDeleteListener listener) {
        this.onDeleteListener = listener;
    }

    public interface OnDeleteListener
    {
        void onDelete();
    }

    @Override
    public int getCount()
    {
        return json_reminders.size();
    }

    @Override
    public Object getItem(int position)
    {
        return json_reminders.get(position);
    }

    @Override
    public long getItemId(int id)
    {
        return id;
    }

    @Override
    public View getView(int position, View contextView, ViewGroup parent)
    {
        ReminderAdapter.HolderView holderView;

        if(contextView == null)
        {
            contextView = LayoutInflater.from(context).inflate(R.layout.alarm_item,
                    parent, false);
            holderView = new ReminderAdapter.HolderView(contextView);
            contextView.setTag(holderView);
        }
        else
        {
            holderView = (ReminderAdapter.HolderView) contextView.getTag();
        }

        Gson gson = new Gson();
        Reminder reminder = gson.fromJson(json_reminders.get(position), Reminder.class);

        holderView.time.setText(reminder.getRing_time());
        holderView.days.setText(reminder.getDays_to_ring().toString());
        holderView.reminder.setText(reminder.getReminder_text());
        holderView.switcher.setChecked(switchStates.get(position)); // set Switch state

        holderView.switcher.setOnCheckedChangeListener((compoundButton, b) ->
        {
            switchStates.set(position, b);
            System.out.println("Change at position: " + position + " to value " + b);
            System.out.println(getSwitchStates());
        });

        holderView.delete.setOnClickListener(view ->
        {
            json_reminders.remove(position);
            switchStates.remove(position); // remove Switch state
            removeReminderFromSharedPreferences(json_reminders);
            notifyDataSetChanged();

            if (onDeleteListener != null)
            {
                onDeleteListener.onDelete();
            }
        });

        return contextView;
    }

    public ArrayList<Boolean> getSwitchStates()
    {
        return switchStates;
    }


    private void removeReminderFromSharedPreferences(ArrayList<String> reminders)
    {
        SharedPreferences sp = context.getSharedPreferences("com.example.humancomputerinteraction", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        HashSet<String> alarmSet = new HashSet<>(reminders);
        editor.putStringSet("alarms", alarmSet);
        editor.apply();
    }

    private static class HolderView
    {
        private final TextView time;
        private final TextView days;
        private final TextView reminder;
        private final Switch switcher;
        private final Button delete;

        public HolderView(View view)
        {
            time = (TextView) view.findViewById(R.id.alarm_time);
            days = (TextView) view.findViewById(R.id.days_to_ring);
            reminder = (TextView) view.findViewById(R.id.reminder_note);
            switcher = (Switch) view.findViewById(R.id.switch_on_off);
            delete = (Button) view.findViewById(R.id.delete_alarm);
        }
    }
}
