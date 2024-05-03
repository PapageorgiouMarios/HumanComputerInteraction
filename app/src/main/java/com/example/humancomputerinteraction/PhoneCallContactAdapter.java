package com.example.humancomputerinteraction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class PhoneCallContactAdapter extends BaseAdapter
{
    private final Context context;
    private final ArrayList<String> json_contacts;

    private CallButtonClickListener buttonClickListener;
    public PhoneCallContactAdapter(Context context, ArrayList<String> json_contacts)
    {
        this.context = context;
        this.json_contacts = json_contacts;
    }

    public void setCallButtonClickListener(CallButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    @Override
    public int getCount() {
        return json_contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return json_contacts.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View contextView, ViewGroup parent)
    {
        PhoneCallContactAdapter.HolderView holderView;

        if(contextView == null)
        {
            contextView = LayoutInflater.from(context).inflate(R.layout.phone_call_contact_item,
                    parent, false);
            holderView = new PhoneCallContactAdapter.HolderView(contextView);
            contextView.setTag(holderView);
        }
        else
        {
            holderView = (PhoneCallContactAdapter.HolderView) contextView.getTag();
        }

        Gson gson = new Gson();
        Contact contact = gson.fromJson(json_contacts.get(position), Contact.class);

        holderView.first_name.setText(contact.getFirst_name());
        holderView.last_name.setText(contact.getLast_name());

        holderView.call_button.setOnClickListener(v -> {
            if (buttonClickListener != null) {
                Gson gson2 = new Gson();
                Contact contact2 = gson2.fromJson(json_contacts.get(position), Contact.class);
                buttonClickListener.onCallButtonClicked(contact2.getPhone_number());
            }
        });

        return contextView;
    }

    private static class HolderView
    {
        private final TextView first_name;
        private final TextView last_name;
        private final Button call_button;

        public HolderView(View view)
        {
            first_name = (TextView) view.findViewById(R.id.who_to_call_first_name);
            last_name = (TextView) view.findViewById(R.id.who_to_call_last_name);
            call_button = (Button) view.findViewById(R.id.who_to_call_button);
        }
    }

    public interface CallButtonClickListener
    {
        void onCallButtonClicked(String phoneNumber);
    }
}
