package com.example.humancomputerinteraction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EmergencyAdapter extends BaseAdapter
{
    private final Context context;
    private final ArrayList<EmergencyContactModel>  emergencyContacts;

    public EmergencyAdapter(Context context, ArrayList<EmergencyContactModel> emergencyContacts)
    {
        this.context = context;
        this.emergencyContacts = emergencyContacts;
    }

    @Override
    public int getCount()
    {
        return emergencyContacts.size();
    }

    @Override
    public Object getItem(int position)
    {
        return emergencyContacts.get(position);
    }

    @Override
    public long getItemId(int id)
    {
        return id;
    }

    @Override
    public View getView(int position, View contextView, ViewGroup parent)
    {
        HolderView holderView;

        if(contextView == null)
        {
            contextView = LayoutInflater.from(context).inflate(R.layout.emergency_contact_item_view,
                    parent, false);
            holderView = new HolderView(contextView);
            contextView.setTag(holderView);
        }
        else
        {
            holderView = (HolderView) contextView.getTag();
        }

        EmergencyContactModel contact = emergencyContacts.get(position);

        holderView.icon.setImageResource(contact.getIconId());
        holderView.name.setText(contact.getEmergency_contact_name());
        holderView.number.setText(contact.getEmergency_contact_number());

        return contextView;
    }

    private static class HolderView
    {
        private final ImageView icon;
        private final TextView name;
        private final TextView number;

        public HolderView(View view)
        {
            icon = (ImageView) view.findViewById(R.id.emergency_pic);
            name = (TextView) view.findViewById(R.id.emergency_name);
            number = (TextView) view.findViewById(R.id.emergency_number);
        }
    }
}
