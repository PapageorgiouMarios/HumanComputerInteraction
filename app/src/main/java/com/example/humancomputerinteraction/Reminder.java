package com.example.humancomputerinteraction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Reminder
{
    private String reminder_text;
    private String ring_time;
    private ArrayList<String> days_to_ring;

    public Reminder()
    {
        this.reminder_text = "";
        this.ring_time = "00:00";
        this.days_to_ring = new ArrayList<String>();
    }

    public Reminder(String reminder_text, String ring_time, ArrayList<String> days_to_ring)
    {
        this.reminder_text = reminder_text;
        this.ring_time = ring_time;
        this.days_to_ring = days_to_ring;
    }

    public void printStats()
    {
        System.out.println("1) Remind me: " + reminder_text);
        System.out.println("2) At: " + ring_time);
        System.out.println("3) Every: " + days_to_ring);
    }

    public String getReminder_text()
    {
        return reminder_text;
    }

    public void setReminder_text(String reminder_text)
    {
        this.reminder_text = reminder_text;
    }

    public String getRing_time() {

        return ring_time;
    }

    public void setRing_time(String ring_time)
    {
        this.ring_time = ring_time;
    }

    public ArrayList<String> getDays_to_ring()
    {
        return days_to_ring;
    }

    public void setDays_to_ring(ArrayList<String> days_to_ring)
    {
        this.days_to_ring = days_to_ring;
    }
}
