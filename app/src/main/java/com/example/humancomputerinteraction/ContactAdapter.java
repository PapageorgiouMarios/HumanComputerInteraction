package com.example.humancomputerinteraction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter
{
    private final Context context;
    private final ArrayList<String> json_contacts;

    public ContactAdapter(Context context, ArrayList<String> contacts)
    {
        this.context = context;
        this.json_contacts = contacts;
    }

    @Override
    public int getCount()
    {
        return json_contacts.size();
    }

    @Override
    public Object getItem(int position)
    {
        return json_contacts.get(position);
    }

    @Override
    public long getItemId(int id)
    {
        return id;
    }

    @Override
    public View getView(int position, View contextView, ViewGroup parent)
    {
        ContactAdapter.HolderView holderView;

        if(contextView == null)
        {
            contextView = LayoutInflater.from(context).inflate(R.layout.contact_item,
                    parent, false);
            holderView = new ContactAdapter.HolderView(contextView);
            contextView.setTag(holderView);
        }
        else
        {
            holderView = (ContactAdapter.HolderView) contextView.getTag();
        }

        Gson gson = new Gson();
        Contact contact = gson.fromJson(json_contacts.get(position), Contact.class);

        holderView.first_name.setText(contact.getFirst_name());
        holderView.last_name.setText(contact.getLast_name());
        holderView.phone_number.setText(contact.getPhone_number());

        return contextView;
    }

    private static class HolderView
    {
        private final TextView first_name;
        private final TextView last_name;
        private final TextView phone_number;

        public HolderView(View view)
        {
            first_name = (TextView) view.findViewById(R.id.contact_first_name);
            last_name = (TextView) view.findViewById(R.id.contact_last_name);
            phone_number = (TextView) view.findViewById(R.id.contact_phone_number);
        }
    }
}
