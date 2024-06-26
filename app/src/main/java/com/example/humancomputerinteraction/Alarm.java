package com.example.humancomputerinteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class Alarm extends AppCompatActivity
{
    Button add_alarm;
    ListView alarm_list;
    RelativeLayout no_alarms_layout;
    TextView no_alarms_text;
    static ArrayList<String> alarms;
    SharedPreferences sp;

    private final Handler handler = new Handler();
    private final Runnable checkAlarmsRunnable = new Runnable()
    {
        @Override
        public void run() {
            checkAlarms();
            handler.postDelayed(this, 60000); // Check alarms every 60 seconds
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        add_alarm = (Button) findViewById(R.id.add_alarm);
        alarm_list = (ListView) findViewById(R.id.alarm_list);
        no_alarms_layout = (RelativeLayout) findViewById(R.id.empty_alarm_list);
        no_alarms_text = (TextView) findViewById(R.id.empty_alarm_text);

        alarms = new ArrayList<>();

        sp = this.getSharedPreferences("com.example.humancomputerinteraction", Context.MODE_PRIVATE);
        HashSet<String> alarmSet = (HashSet<String>) sp.getStringSet("alarms", null);

        if(alarmSet == null || alarmSet.isEmpty())
        {
            no_alarms_text.setVisibility(View.VISIBLE);
            no_alarms_layout.setVisibility(View.VISIBLE);
        }
        else
        {
            no_alarms_text.setVisibility(View.INVISIBLE);
            no_alarms_layout.setVisibility(View.INVISIBLE);
            alarms = new ArrayList<>(alarmSet);
        }

        ReminderAdapter reminder_adapter = new ReminderAdapter(Alarm.this, alarms);
        reminder_adapter.setOnDeleteListener(this::updateNoAlarmsVisibility);
        alarm_list.setAdapter(reminder_adapter);

        System.out.println("Switch values: " + reminder_adapter.getSwitchStates());

        Log.d("Marios", "Start handler thread execution");
        startCheckingAlarms();

        add_alarm.setOnClickListener(view -> {
            Intent add_reminder = new Intent(Alarm.this, AddAlarm.class);
            startActivity(add_reminder);
        });

    }

    private void checkAlarms()
    {
        ReminderAdapter reminder_adapter = (ReminderAdapter) alarm_list.getAdapter();
        Log.d("Marios", "Get the adapter of the alarms");

        if (reminder_adapter.getSwitchStates().size() > 0)
        {
            Log.d("Marios", "There are alarms available");
            Log.d("Marios", "Start checking");
            Log.d("Marios", "Switches states: " + reminder_adapter.getSwitchStates());
            for (int j = 0; j < reminder_adapter.getSwitchStates().size(); j++)
            {
                Log.d("Marios", "Switches states at position : " + j + " " + reminder_adapter.getSwitchStates().get(j));
                if (reminder_adapter.getSwitchStates().get(j))
                {
                    Log.d("Marios", "Alarm is on, position " + j);
                    Gson gson = new Gson();
                    Reminder r = gson.fromJson(alarms.get(j), Reminder.class);

                    String r_text = r.getReminder_text();
                    String r_time = r.getRing_time();
                    ArrayList<String> r_days_to_ring = r.getDays_to_ring();

                    Calendar calendar = Calendar.getInstance();
                    String currentTime = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE));

                    String currentDayOfWeek;
                    switch (calendar.get(Calendar.DAY_OF_WEEK))
                    {
                        case Calendar.SUNDAY:
                            currentDayOfWeek = "Sunday";
                            break;
                        case Calendar.MONDAY:
                            currentDayOfWeek = "Monday";
                            break;
                        case Calendar.TUESDAY:
                            currentDayOfWeek = "Tuesday";
                            break;
                        case Calendar.WEDNESDAY:
                            currentDayOfWeek = "Wednesday";
                            break;
                        case Calendar.THURSDAY:
                            currentDayOfWeek = "Thursday";
                            break;
                        case Calendar.FRIDAY:
                            currentDayOfWeek = "Friday";
                            break;
                        case Calendar.SATURDAY:
                            currentDayOfWeek = "Saturday";
                            break;
                        default:
                            currentDayOfWeek = "";
                            break;
                    }

                    Log.d("Marios", "Reminder class object: " + r);
                    Log.d("Marios", "Alarm's reminder: " + r_text);
                    Log.d("Marios", "Alarm's time: " + r_time);
                    Log.d("Marios", "Alarm's days: " + r_days_to_ring);
                    Log.d("Marios", "Current Time: " + currentTime);
                    Log.d("Marios", "Current Day: " + currentDayOfWeek);

                    if (currentTime.equals(r_time))
                    {
                        Log.d("Marios", "------------------------------------------------------------------------");
                        Log.d("Marios", "RING RING RING RING RING RING RING");
                        Log.d("Marios", "------------------------------------------------------------------------");
                    }
                }
            }
        }
    }


    private void startCheckingAlarms() {
        handler.post(checkAlarmsRunnable);
    }

    private void stopCheckingAlarms() {
        handler.removeCallbacks(checkAlarmsRunnable);
    }

    private void updateNoAlarmsVisibility()
    {
        if (alarms == null || alarms.isEmpty())
        {
            no_alarms_text.setVisibility(View.VISIBLE);
            no_alarms_layout.setVisibility(View.VISIBLE);
        }
        else
        {
            no_alarms_text.setVisibility(View.INVISIBLE);
            no_alarms_layout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onDestroy()
    {
        System.out.println("On Destroy method called");
        super.onDestroy();
        stopCheckingAlarms();
    }
    @Override
    public void onBackPressed() {
        Intent main_window = new Intent(getApplicationContext(), MainScreen.class);
        startActivity(main_window);
    }
}