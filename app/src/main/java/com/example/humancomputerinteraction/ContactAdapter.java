package com.example.humancomputerinteraction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter
{
    private final Context context;
    private final ArrayList<Contact> contacts;

    public ContactAdapter(Context context, ArrayList<Contact> contacts)
    {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public int getCount()
    {
        return contacts.size();
    }

    @Override
    public Object getItem(int position)
    {
        return contacts.get(position);
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

        Contact contact = contacts.get(position);

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
