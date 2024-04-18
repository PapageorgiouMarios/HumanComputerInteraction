package com.example.humancomputerinteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;


public class AddAlarm extends AppCompatActivity
{
    EditText reminder; // The user can write any kind of note when the alarm is activated
    Button ring_once; // The user can choose the alarm to activate only once in a day
    // The one time alarm depends only on the hour selected
    Button ring_every_week; // The user can choose the alarm to activate specific days of the week
    // The weekly alarm depends on both the day and the hour selected

    boolean ring_once_pressed = false; // Checks if the Once button has been pressed
    boolean ring_every_week_pressed = false; // Checks if the Weekly button has been pressed

    TextView ring_every_text; // A text which is presented whenever Weekly button is pressed
    RelativeLayout ring_every_buttons_layout; // This layout is visible only when Weekly button is
    // pressed. The layout contains a button for each days of the week so the user can choose
    boolean[] day_buttons_pressed; // a boolean array to confirm which day button has been pressed
    String[] days_of_the_week; // an array of all days' names

    //---------------------------------ALL WEEK'S DAYS BUTTONS--------------------------------------
    Button sunday_B;
    Button monday_B;
    Button tuesday_B;
    Button wednesday_B;
    Button thursday_B;
    Button friday_B;
    Button saturday_B;
    //----------------------------------------------------------------------------------------------
    RelativeLayout time_selection_layout; // This layout is used to select a specific hour for
    // the alarm to be activated
    TextView time_selected; // When a specific hour is selected it should be visible on the screen
    int alarm_hour; // The hour the alarm activates
    int alarm_minute; // The minute the alarm activates
    boolean once_or_weekly; // Checks if at least one of the two buttons has been selected
    boolean days_of_week_chosen; // In case the weekly button has been pressed we need to make sure
    // that at least one day of the week has been selected
    boolean time_chosen; // Checks if the alarm has a proper time set
    Button save_alarm; // When this button is pressed the alarm is saved and it must be in the
    // Alarm Activity Listview

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        sp = this.getSharedPreferences("com.example.humancomputerinteraction", Context.MODE_PRIVATE);

        reminder = (EditText) findViewById(R.id.reminder_title);

        ring_every_text = (TextView) findViewById(R.id.ring_every_text);
        ring_every_buttons_layout = (RelativeLayout) findViewById(R.id.weekly_alarm);

        ring_once = (Button) findViewById(R.id.one_time_ring);
        ring_every_week = (Button) findViewById(R.id.weekly_ring);

        ring_once.setOnClickListener(view ->
        {
            PressOnce();
        });

        ring_every_week.setOnClickListener(view ->
        {
            PressWeekly();
        });

        sunday_B = (Button) findViewById(R.id.sunday_button);
        monday_B = (Button) findViewById(R.id.monday_button);
        tuesday_B = (Button) findViewById(R.id.tuesday_button);
        wednesday_B = (Button) findViewById(R.id.wednesday_button);
        thursday_B = (Button) findViewById(R.id.thursday_button);
        friday_B = (Button) findViewById(R.id.friday_button);
        saturday_B = (Button) findViewById(R.id.saturday_button);

        day_buttons_pressed = new boolean[]{false, false, false, false, false, false, false};

        days_of_the_week = new String[]
                {
                        "Sunday", "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday"
                };

        sunday_B.setOnClickListener(view ->
        {
            dayButtonPressed(sunday_B, 0);
        });

        monday_B.setOnClickListener(view ->
        {
            dayButtonPressed(monday_B, 1);
        });

        tuesday_B.setOnClickListener(view ->
        {
            dayButtonPressed(tuesday_B, 2);
        });

        wednesday_B.setOnClickListener(view ->
        {
            dayButtonPressed(wednesday_B, 3);
        });

        thursday_B.setOnClickListener(view ->
        {
            dayButtonPressed(thursday_B, 4);
        });

        friday_B.setOnClickListener(view ->
        {
            dayButtonPressed(friday_B, 5);
        });

        saturday_B.setOnClickListener(view ->
        {
            dayButtonPressed(saturday_B, 6);
        });

        time_selection_layout = (RelativeLayout) findViewById(R.id.time);
        time_selected = (TextView) findViewById(R.id.set_time);

        time_selection_layout.setOnClickListener(view -> popTimePicker());

        save_alarm = (Button) findViewById(R.id.add_new_alarm);
        save_alarm.setOnClickListener(view ->
        {
            if(CheckAlarmSettings())
            {
                System.out.println("Alarm settings are ready to save!");

                String reminderContent = reminder.getText().toString();
                String timeToRing = time_selected.getText().toString();
                ArrayList<String> alarmDays = new ArrayList<>();

                if(ring_every_week_pressed)
                {
                    for(int day = 0; day < day_buttons_pressed.length; day++)
                    {
                        if(day_buttons_pressed[day])
                        {
                            alarmDays.add(days_of_the_week[day]);
                        }
                    }
                }

                Reminder a = new Reminder(reminderContent, timeToRing, alarmDays);
                a.printStats();

                Gson gson = new Gson();
                String json = gson.toJson(a);
                Alarm.alarms.add(json);
                HashSet<String> alarmSet = new HashSet<>(Alarm.alarms);
                sp.edit().putStringSet("alarms", alarmSet).apply();

                Intent back_to_alarm = new Intent(AddAlarm.this, Alarm.class);
                startActivity(back_to_alarm);
            }
            else
            {
                System.out.println("Problem with alarm parameters");
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent back_to_alarm = new Intent(AddAlarm.this, Alarm.class);
        startActivity(back_to_alarm);
    }

    private void selectButton(Button b)
    {
        b.setBackgroundColor(Color.parseColor("#FFDA0303"));
    }

    private void unselectButton(Button b)
    {
        b.setBackgroundColor(Color.parseColor("#FF03DAC5"));
    }

    private void showWeeklySettings()
    {
        ring_every_text.setVisibility(View.VISIBLE);
        ring_every_buttons_layout.setVisibility(View.VISIBLE);
    }

    private void hideWeeklySettings()
    {
        ring_every_text.setVisibility(View.GONE);
        ring_every_buttons_layout.setVisibility(View.GONE);

        unselectButton(sunday_B);
        unselectButton(monday_B);
        unselectButton(tuesday_B);
        unselectButton(wednesday_B);
        unselectButton(thursday_B);
        unselectButton(friday_B);
        unselectButton(saturday_B);

        day_buttons_pressed = new boolean[]{false, false, false, false, false, false, false};
    }

    private void dayButtonPressed(Button b, int i)
    {
        if(!day_buttons_pressed[i])
        {
            selectButton(b);
            day_buttons_pressed[i] = true;
        }
        else
        {
            unselectButton(b);
            day_buttons_pressed[i] = false;
        }
    }

    private void PressOnce()
    {
        if(!ring_once_pressed)
        {
            selectButton(ring_once);
            ring_once_pressed = true;
            hideWeeklySettings();
        }
        else
        {
            unselectButton(ring_once);
            ring_once_pressed = false;
            hideWeeklySettings();
        }

        if(ring_every_week_pressed)
        {
            unselectButton(ring_every_week);
            ring_every_week_pressed = false;
            hideWeeklySettings();
        }
    }

    private void PressWeekly()
    {
        if(!ring_every_week_pressed)
        {
            selectButton(ring_every_week);
            ring_every_week_pressed = true;
            showWeeklySettings();
        }
        else
        {
            unselectButton(ring_every_week);
            ring_every_week_pressed = false;
            hideWeeklySettings();
        }

        if(ring_once_pressed)
        {
            unselectButton(ring_once);
            ring_once_pressed = false;
            showWeeklySettings();
        }
    }

    public void popTimePicker()
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) ->
        {
            alarm_hour = selectedHour;
            alarm_minute = selectedMinute;
            time_selected.setText(String.format(Locale.getDefault(), "%02d:%02d",
                    alarm_hour, alarm_minute));
        };
        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener,
                alarm_hour, alarm_minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    private boolean CheckAlarmSettings()
    {
        once_or_weekly = ring_once_pressed || ring_every_week_pressed;
        System.out.println("?)Has alarm set to ring once or weekly: " + once_or_weekly);

        if(ring_every_week_pressed)
        {
            System.out.println("!)Weekly button has been chosen");
            System.out.println("!)Check if at least one day has been chosen");

            for (boolean b : day_buttons_pressed)
            {
                System.out.println(b);

                if (b)
                {
                    days_of_week_chosen = true;
                    break;
                }
                days_of_week_chosen = false;
            }
        }

        boolean weekly_settings_proper = (ring_every_week_pressed && days_of_week_chosen) ||
                (!ring_every_week_pressed && !days_of_week_chosen);

        System.out.println("?)Selected time: " + time_selected.getText());
        time_chosen = time_selected.getText() != "";
        System.out.println("?)Has the alarm time: " + time_chosen);

        return once_or_weekly && time_chosen && weekly_settings_proper;
    }
}